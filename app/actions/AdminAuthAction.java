package actions;

import models.User;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public class AdminAuthAction extends Action<BasicAuth> {

	@Override
	public Result call(Context context) throws Throwable {
		String username = context.session().get("username");

		if (username != null && User.getUserByName(username).isAdmin()) {
			return delegate.call(context);
		} else {
			return redirect("/");
		}
	}
}
