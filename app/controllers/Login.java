package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.Context;
import views.html.login;

public class Login extends Controller {

	public static class LoginForm {

		public String username;
		public String password;

		public String validate() {
			if (!username.equals("peter") || !password.equals("password")) {
				return "Invalid user or password";
			} else {
				Context.current().session().put("username", username);
			}
				
			return null;
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
