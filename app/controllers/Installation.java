package controllers;

import models.Configuration;
import models.User;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.install;

public class Installation extends Controller {

	public static class InstallationForm {
		protected static final String USERNAME_AND_PASSWORD_MUST_BE_FILLED = "Username and password must be filled!";

		@Required
		public String username;

		@Required
		public String password;

		public String validate() {
			if (username == "" || password == "") {
				return USERNAME_AND_PASSWORD_MUST_BE_FILLED;
			}
			return null;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static Result install() {
		return ok(install.render(form(Installation.InstallationForm.class)));
	}

	public static Result installSystem() {
		Form<InstallationForm> installationForm = form(InstallationForm.class)
				.bindFromRequest();
		if (installationForm.hasErrors()) {
			return badRequest(install.render(installationForm));
		} else {
			User newUser = User
					.createUserFromInstallationForm(installationForm);
			newUser.save();
			Configuration configuration = new Configuration(
					Configuration.KEY_INSTALLED, "true");
			configuration.save();
			return redirect("/login");
		}
	}
}
