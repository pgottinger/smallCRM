package controllers;

import actions.AdminAuth;
import models.User;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.createUser;

@AdminAuth
public class UserAdmin extends Controller {

	public static final String SHOW_CREATE_USER_FORM = "/admin/showCreateUserForm";

	public static class CreateUserForm {
		protected static final String USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED = "Username, password and email must be filled!";

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
			return redirect(SHOW_CREATE_USER_FORM);
		}
	}

}
