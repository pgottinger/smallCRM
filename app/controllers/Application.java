package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;
import actions.BasicAuth;
import views.html.index;

@BasicAuth
public class Application extends Controller {

	public static Result index() {
		User currentUser = User.getUserByName(Context.current().session()
				.get("username"));
		return ok(index.render());
	}
}
