package actions;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import views.html.login;

public class BasicAuthAction extends Action<BasicAuth> {

	@Override
	public Result call(Context context) throws Throwable {
		String username = context.session().get("username");
		
		if(username != null) {
			return delegate.call(context);
		} else {
			return ok(login.render());
		}
	}
}
