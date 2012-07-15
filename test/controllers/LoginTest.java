package controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.session;

import java.util.HashMap;
import java.util.Map;

import models.User;

import org.fluentlenium.core.filter.matcher.ContainsMatcher;
import org.junit.Ignore;
import org.junit.Test;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.test.Helpers;
import controllers.Login.LoginForm;

public class LoginTest {

	@Test
	public void testValidateSuccessful() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				LoginForm form = new LoginForm();
				form.username = "dummyUser";
				form.password = "dummyPassword";

				assertNull(form.validate());
			}
		});
	}

	@Test
	public void testValidateFailOnEmptyUser() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				LoginForm form = new LoginForm();
				form.username = "";
				form.password = "dummyPassword";

				assertThat(form.validate(),
						is(LoginForm.INVALID_USER_OR_PASSWORD));
			}
		});
	}

	@Test
	public void testValidateFailOnEmptyPassword() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				LoginForm form = new LoginForm();
				form.username = "dummyUser";
				form.password = "";

				assertThat(form.validate(),
						is(LoginForm.INVALID_USER_OR_PASSWORD));
			}
		});
	}

	@Test
	public void testValidateFailOnEmptyUsernameAndPassword() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				LoginForm form = new LoginForm();
				form.username = "";
				form.password = "";

				assertThat(form.validate(),
						is(LoginForm.INVALID_USER_OR_PASSWORD));
			}
		});
	}

	@Test
	public void testLogout() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {

				Result result = callAction(controllers.routes.ref.Login
						.logout(),
						fakeRequest().withSession("username", "dummyUser"));

				assertNull(session(result).get("username"));
			}
		});
	}

	@Test
	public void testLogin() {
		Result result = callAction(controllers.routes.ref.Login.login());

		assertTrue(contentAsString(result).contains("<h1>Login</h1>"));
	}

	@Test
	public void testAuthenticateSuccessful() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				Map<String, String> data = new HashMap<String, String>();
				data.put("username", "dummyUser");
				data.put("password", "dummyPassword");

				Result result = callAction(
						controllers.routes.ref.Login.authenticate(),
						fakeRequest().withFormUrlEncodedBody(data));

				assertNotNull(session(result).get("username"));
				assertThat(session(result).get("username"), is("dummyUser"));
			}
		});
	}

	@Test
	public void testAuthenticationFailureWhenUserIsEmpty() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				Map<String, String> data = new HashMap<String, String>();
				data.put("username", "");
				data.put("password", "dummyPassword");

				Result result = callAction(
						controllers.routes.ref.Login.authenticate(),
						fakeRequest().withFormUrlEncodedBody(data));

				assertNull(session(result).get("username"));
			}
		});
	}

	@Test
	public void testAuthenticationFailureWhenPasswordIsEmpty() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				Map<String, String> data = new HashMap<String, String>();
				data.put("username", "dummyUser");
				data.put("password", "");

				Result result = callAction(
						controllers.routes.ref.Login.authenticate(),
						fakeRequest().withFormUrlEncodedBody(data));

				assertNull(session(result).get("username"));
			}
		});
	}

	@Test
	public void testAuthenticationFailureWhenUserAndPasswordAreEmpty() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				User testUser = new User("dummyUser", "dummyPassword", true);
				testUser.save();

				Map<String, String> data = new HashMap<String, String>();
				data.put("username", "");
				data.put("password", "");

				Result result = callAction(
						controllers.routes.ref.Login.authenticate(),
						fakeRequest().withFormUrlEncodedBody(data));

				assertNull(session(result).get("username"));
			}
		});
	}

}
