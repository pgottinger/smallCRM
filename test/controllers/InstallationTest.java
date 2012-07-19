package controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

import models.Configuration;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeApplication;
import controllers.Installation.InstallationForm;

public class InstallationTest {

	private FakeApplication app;

	@Before
	public void setup() {
		app = fakeApplication(inMemoryDatabase());
		start(app);
	}

	@After
	public void tearDown() {
		stop(app);
	}

	@Test
	public void testUserFormValidationBothFieldsEmpty() {
		InstallationForm form = new InstallationForm();
		form.username = "";
		form.password = "";
		form.email = "";
		assertEquals(form.validate(),
				InstallationForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationUserEmpty() {
		InstallationForm form = new InstallationForm();
		form.username = "";
		form.password = "dummyPassword";
		form.email = "dummy@email.com";

		assertEquals(form.validate(),
				InstallationForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationPasswordEmpty() {
		InstallationForm form = new InstallationForm();
		form.username = "dummyUser";
		form.password = "";
		form.email = "dummy@email.com";

		assertEquals(form.validate(),
				InstallationForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationEmailEmpty() {
		InstallationForm form = new InstallationForm();
		form.username = "dummyUser";
		form.password = "dummyPassword";
		form.email = "";

		assertEquals(form.validate(),
				InstallationForm.USERNAME_PASSWORD_AND_EMAIL_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationSuccess() {
		InstallationForm form = new InstallationForm();
		form.username = "dummyUser";
		form.password = "dummyPassword";
		form.email = "dummy@email.com";

		assertNull(form.validate());
	}

	@Test
	public void testInstall() {
		Result result = callAction(controllers.routes.ref.Installation
				.install());

		assertThat(status(result), is(OK));
	}

	@Test
	public void installSystem() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "dummyPassword");
		data.put("email", "user@email.com");

		callAction(controllers.routes.ref.Installation.installSystem(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertNotNull(User.getUserByName("dummyUser"));
		assertTrue(Configuration.isSystemAlreadyInstalled());
	}

	@Test
	public void dontInstallSystemWhenUsernameEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "");
		data.put("password", "dummyPassword");
		data.put("email", "user@email.com");

		callAction(controllers.routes.ref.Installation.installSystem(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertThat(User.getNumberOfUsers(), is(0));
	}

	@Test
	public void dontInstallSystemWhenPasswordEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "");
		data.put("email", "user@email.com");

		callAction(controllers.routes.ref.Installation.installSystem(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertNull(User.getUserByName("dummyUser"));
	}

	@Test
	public void dontInstallSystemWhenEmailEmpty() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", "dummyUser");
		data.put("password", "dummyPassword");
		data.put("email", "");

		callAction(controllers.routes.ref.Installation.installSystem(),
				fakeRequest().withFormUrlEncodedBody(data));

		assertThat(User.getNumberOfUsers(), is(0));
	}

}
