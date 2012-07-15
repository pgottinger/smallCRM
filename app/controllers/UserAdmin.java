package controllers;

import models.User;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createUser;

public class UserAdmin extends Controller {

	public static class CreateUserForm {
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

	public static Result showCreateUserForm() {
		return ok(createUser.render(form(UserAdmin.CreateUserForm.class)));
	}

	public static Result createUser() {
		Form<CreateUserForm> createUserForm = form(CreateUserForm.class)
				.bindFromRequest();
		if (createUserForm.hasErrors()) {
			return badRequest(createUser.render(createUserForm));
		} else {
			User newUser = new User(createUserForm);
			newUser.save();
			return redirect(routes.UserAdmin.showCreateUserForm());
		}
	}

}
