package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createUser;

public class UserAdmin extends Controller {

	public static class CreateUserForm {
		public String username;
		public String password;

		public String validate() {
			if (username == "" || password == "") {
				return "Username and password must be filled!";
			}
			return null;
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
