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
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import static play.test.Helpers.inMemoryDatabase;

import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.Test;

import play.api.test.Helpers;
import play.mvc.Result;
import controllers.UserAdmin.CreateUserForm;

public class UserAdminTest {

	@Test
	public void testUserFormValidationBothFieldsEmpty() {
		CreateUserForm form = new CreateUserForm();
		form.username = "";
		form.password = "";

		assertEquals(form.validate(),
				CreateUserForm.USERNAME_AND_PASSWORD_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationUserEmpty() {
		CreateUserForm form = new CreateUserForm();
		form.username = "";
		form.password = "dummyPassword";

		assertEquals(form.validate(),
				CreateUserForm.USERNAME_AND_PASSWORD_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationPasswordEmpty() {
		CreateUserForm form = new CreateUserForm();
		form.username = "dummyUser";
		form.password = "";

		assertEquals(form.validate(),
				CreateUserForm.USERNAME_AND_PASSWORD_MUST_BE_FILLED);
	}

	@Test
	public void testUserFormValidationSuccess() {
		CreateUserForm form = new CreateUserForm();
		form.username = "dummyUser";
		form.password = "dummyPassword";

		assertNull(form.validate());
	}

	@Test
	public void testShowUserForm() {
		Result result = callAction(controllers.routes.ref.UserAdmin
				.showCreateUserForm());

		assertThat(status(result), is(OK));
	}

	@Test
	public void createUser() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				Map<String, String> data = new HashMap<String, String>();
				data.put("username", "dummyUser");
				data.put("password", "dummyPassword");

				callAction(controllers.routes.ref.UserAdmin.createUser(),
						fakeRequest().withFormUrlEncodedBody(data));

				assertNotNull(User.getUserByName("dummyUser"));
			}
		});
	}

	@Test
	public void dontCreateUserWhenPasswordEmpty() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				Map<String, String> data = new HashMap<String, String>();
				data.put("username", "dummyUser");
				data.put("password", "");

				callAction(controllers.routes.ref.UserAdmin.createUser(),
						fakeRequest().withFormUrlEncodedBody(data));

				assertNull(User.getUserByName("dummyUser"));
			}
		});
	}
}
