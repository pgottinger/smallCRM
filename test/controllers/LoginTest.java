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
import static play.test.Helpers.session;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

import java.util.HashMap;
import java.util.Map;

import models.Configuration;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeApplication;
import controllers.Login.LoginForm;

public class LoginTest {

	private User testUser;
	private FakeApplication app;

	@Before
	public void setup() {
		app = fakeApplication(inMemoryDatabase());
		start(app);

		testUser = createTestUser();
	}

	@After
	public void tearDown() {
		testUser.delete();
		testUser = null;

		stop(app);
	}

	@Test
	public void testValidateSuccessful() {
		LoginForm form = new LoginForm();
		form.username = "dummyUser";
		form.password = "dummyPassword";

		assertNull(form.validate());
	}

	@Test
	public void testValidateFailOnEmptyUser() {
		LoginForm form = new LoginForm();
		form.username = "";
		form.password = "dummyPassword";

		assertThat(form.validate(), is(LoginForm.INVALID_USER_OR_PASSWORD));
	}

	@Test
	public void testValidateFailOnEmptyPassword() {
		LoginForm form = new LoginForm();
		form.username = "dummyUser";
		form.password = "";

		assertThat(form.validate(), is(LoginForm.INVALID_USER_OR_PASSWORD));
	}

	@Test
	public void testValidateFailOnEmptyUsernameAndPassword() {
		LoginForm form = new LoginForm();
		form.username = "";
		form.password = "";

		assertThat(form.validate(), is(LoginForm.INVALID_USER_OR_PASSWORD));
	}

	@Test
	public void testLogout() {
		Result result = callAction(controllers.routes.ref.Login.logout(),
				fakeRequest().withSession("username", "dummyUser"));

		assertNull(session(result).get("username"));
	}

	@Test
	public void testLoginIfSystemIsInstalled() {
		Configuration configuration = new Configuration(
				Configuration.KEY_INSTALLED, "true");
		configuration.save();
		Result result = callAction(controllers.routes.ref.Login.login());

		assertTrue(contentAsString(result).contains("<h1>Login</h1>"));
	}

	@Test
	public void testAuthenticateSuccessful() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "dummyPassword");

		Result result = callAction(controllers.routes.ref.Login.authenticate(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertNotNull(session(result).get("username"));
		assertThat(session(result).get("username"), is("dummyUser"));
	}

	@Test
	public void testAuthenticationFailureWhenUserIsEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "");
		data.put("password", "dummyPassword");

		Result result = callAction(controllers.routes.ref.Login.authenticate(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertNull(session(result).get("username"));
	}

	@Test
	public void testAuthenticationFailureWhenPasswordIsEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "");

		Result result = callAction(controllers.routes.ref.Login.authenticate(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertNull(session(result).get("username"));
	}

	@Test
	public void testAuthenticationFailureWhenUserAndPasswordAreEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "");
		data.put("password", "");

		Result result = callAction(controllers.routes.ref.Login.authenticate(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertNull(session(result).get("username"));
	}

	private User createTestUser() {
		User testUser = new User("dummyUser", "dummyPassword", true);
		testUser.save();
		return testUser;
	}

}
