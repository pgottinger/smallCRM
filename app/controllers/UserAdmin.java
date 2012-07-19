package controllers;

import actions.AdminAuth;
import models.User;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.Context;
import views.html.createUser;

@AdminAuth
public class UserAdmin extends Controller {

	public static final String SHOW_CREATE_USER_FORM = "/showCreateUserForm";

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
		return ok(createUser.render(form(UserAdmin.CreateUserForm.class),
				User.getLoggedInUser()));
	}

	public static Result createUser() {
		Form<CreateUserForm> createUserForm = form(CreateUserForm.class)
				.bindFromRequest();
		if (createUserForm.hasErrors()) {
			return badRequest(createUser.render(createUserForm,
					User.getLoggedInUser()));
		} else {
			User newUser = new User(createUserForm);
			newUser.save();
			return redirect(SHOW_CREATE_USER_FORM);
		}
	}

}
