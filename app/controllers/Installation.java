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
		protected static final String USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED = "Username and password must be filled!";

		@Required
		public String username;

		@Required
		public String password;

		@Required
		public String email;

		public String validate() {
			if (parameterInvalid(username) || parameterInvalid(password)
					|| parameterInvalid(email)) {
				return USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED;
			}
			return null;
		}

		private boolean parameterInvalid(String parameter) {
			return parameter == null || parameter == "";
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}

	public static Result install() {
		if (Configuration.isSystemAlreadyInstalled()) {
			return redirect("/");
		}
		return ok(install.render(form(Installation.InstallationForm.class)));
	}

	public static Result installSystem() {
		if (Configuration.isSystemAlreadyInstalled()) {
			return redirect("/");
		}

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
