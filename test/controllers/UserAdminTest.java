package controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.status;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeApplication;
import controllers.UserAdmin.CreateUserForm;

public class UserAdminTest {

	private User currentUser;
	private FakeApplication app;

	@Before
	public void setup() {
		app = fakeApplication(inMemoryDatabase());
		start(app);

		currentUser = createCurrentUser();
	}

	@After
	public void tearDown() {
		currentUser.delete();
		currentUser = null;

		stop(app);
	}

	@Test
	public void testUserFormValidationAllFieldsEmpty() {
		CreateUserForm form = new CreateUserForm();
		form.username = "";
		form.password = "";
		form.email = "";

		assertEquals(form.validate(),
				CreateUserForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationUserEmpty() {
		CreateUserForm form = new CreateUserForm();
		form.username = "";
		form.password = "dummyPassword";
		form.email = "dummy@email.com";

		assertEquals(form.validate(),
				CreateUserForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationPasswordEmpty() {
		CreateUserForm form = new CreateUserForm();
		form.username = "dummyUser";
		form.password = "";
		form.email = "dummy@email.com";

		assertEquals(form.validate(),
				CreateUserForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidatioEmailEmpty() {
		CreateUserForm form = new CreateUserForm();
		form.username = "dummyUser";
		form.password = "dummyPassword";
		form.email = "";

		assertEquals(form.validate(),
				CreateUserForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationSuccess() {
		CreateUserForm form = new CreateUserForm();
		form.username = "dummyUser";
		form.password = "dummyPassword";
		form.email = "dummy@email.com";

		assertNull(form.validate());
	}

	@Test
	public void testShowUserForm() {
		Result result = callAction(
				controllers.routes.ref.UserAdmin.showCreateUserForm(),
				fakeRequest()
						.withSession("username", currentUser.getUserName()));

		assertThat(status(result), is(OK));

	}

	@Test
	public void createUser() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "dummyPassword");
		data.put("email", "user@email.com");

		callAction(
				controllers.routes.ref.UserAdmin.createUser(),
				fakeRequest().withFormUrlEncodedBody(data).withSession(
						"username", currentUser.getUserName()));

		assertNotNull(User.getUserByName("dummyUser"));

	}

	@Test
	public void dontCreateUserWhenPasswordEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "");
		data.put("email", "user@email.com");

		callAction(controllers.routes.ref.UserAdmin.createUser(), fakeRequest()
				.withFormUrlEncodedBody(data));

		assertNull(User.getUserByName("dummyUser"));
	}

	@Test
	public void dontCreateUserWhenUsernameEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "");
		data.put("password", "dummyPassword");
		data.put("email", "user@email.com");

		callAction(
				controllers.routes.ref.UserAdmin.createUser(),
				fakeRequest().withFormUrlEncodedBody(data).withSession(
						"username", currentUser.getUserName()));

		assertThat(User.getNumberOfUsers(), is(0));
	}

	@Test
	public void dontCreateUserWhenEMailEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "dummyPassword");
		data.put("email", "");

		callAction(
				controllers.routes.ref.UserAdmin.createUser(),
				fakeRequest().withFormUrlEncodedBody(data).withSession(
						"username", currentUser.getUserName()));

		assertThat(User.getNumberOfUsers(), is(0));
	}

	private User createCurrentUser() {
		currentUser = new User("currentUser", "dummyPassword",
				"dummy@email.com", true);
		currentUser.save();
		return currentUser;
	}
}
