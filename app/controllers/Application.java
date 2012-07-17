package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.Context;
import views.html.index;
import actions.BasicAuth;

@BasicAuth
public class Application extends Controller {

	public static Result index() {
		User currentUser = User.getUserByName(Context.current().session()
				.get("username"));
		return ok(index.render("Your new application is ready, dude.",
				currentUser));
	}

}
