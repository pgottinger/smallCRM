package controllers;

import models.User;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;
import views.html.login;

public class Login extends Controller {

	public static class LoginForm {

		protected static final String INVALID_USER_OR_PASSWORD = "Invalid user or password";

		@Required
		public String username;

		@Required
		public String password;

		public String validate() {
			if (User.authenticate(username, password) == null) {
				return INVALID_USER_OR_PASSWORD;
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

	public static Result authenticate() {
		Form<LoginForm> loginForm = form(LoginForm.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session("username", loginForm.get().username);
			return redirect(routes.Application.index());
		}
	}

	public static Result login() {
		return ok(login.render(form(Login.LoginForm.class)));
	}

	public static Result logout() {
		Context.current().session().remove("username");
		return ok(login.render(form(Login.LoginForm.class)));
	}

}
