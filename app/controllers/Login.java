package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

public class Login extends Controller {

	public static class LoginForm {

		public String username;
		public String password;

		public String validate() {
			if (!username.equals("peter") || !password.equals("password")) {
				return "Invalid user or password";
			}
			return null;
		}

	}

	public static Result authenticate() {
		Form<LoginForm> loginForm = form(LoginForm.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render());
		} else {
			session("username", loginForm.get().username);
			return redirect(routes.Application.index());
		}
	}
	
	public static Result login() {
		return ok(login.render());
	}

}
