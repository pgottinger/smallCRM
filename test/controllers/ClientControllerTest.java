package controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import static play.test.Helpers.stop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Client;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeApplication;

public class ClientControllerTest {

	private static final String FILE_REFERENCE = "0815";
	private static final String MAIL = "max@mustermann.org";
	private static final String MOBILE = "032 / 2385";
	private static final String PHONE = "033 / 4938";
	private static final String CITY = "Musterhausen";
	private static final String ZIP_CODE = "00000";
	private static final String STREET_NUMBER = "3";
	private static final String STREET = "Musterstrasse";
	private static final String CONFESSION = "katholisch";
	private static final String OCCUPATION = "Student";
	private static final String FAMILY_STATUS = "single";
	private static final String GENDER = "male";
	private static final String BIRTHCOUNTRY = "Musterland";
	private static final String BIRTHPLACE = CITY;
	private static final String BIRTHDAY = "01.01.1980";
	private static final String PRENAME = "Max";
	private static final String NAME = "Mustermann";
	private static final String TITLE = "Dr.";
	private static final String FORM_OF_ADRESS = "Herr";
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
	public void createClient() {
		assertTrue(Client.getAllClients().isEmpty());
		createDummyClient();

		List<Client> allClients = Client.getAllClients();
		assertThat(allClients.size(), is(1));
		assertClientData(allClients.get(0));
	}

	@Test
	public void testCreateClientWithoutPhoneNumber() {
		Map<String, String> formData = createFormData();
		formData.put("phone", null);
		createClientForGivenData(formData);
		assertThat(Client.getAllClients().size(), is(1));
	}

	@Test
	public void testCreateClientWithoutMobileNumber() {
		Map<String, String> formData = createFormData();
		formData.put("mobile", null);
		createClientForGivenData(formData);
		assertThat(Client.getAllClients().size(), is(1));
	}

	@Test
	public void testCreateClientWithoutMail() {
		Map<String, String> formData = createFormData();
		formData.put("mail", null);
		createClientForGivenData(formData);
		assertThat(Client.getAllClients().size(), is(1));
	}

	@Test
	public void testCreateClientWithoutErrornousForm() {
		Map<String, String> formData = createFormData();
		formData.put("birthday", "56/15/3424");
		Result result = createClientForGivenData(formData);

		assertTrue(contentAsString(result).contains(
				"Bitte Eingaben pr&uuml;fen!"));
		assertThat(Client.getAllClients().size(), is(0));
	}

	@Test
	public void testShowCreateUserForm() {
		Result result = callAction(
				controllers.routes.ref.ClientController.showCreateClientForm(),
				fakeRequest()
						.withSession("username", currentUser.getUserName()));

		assertThat(status(result), is(OK));
		assertTrue(contentAsString(result).contains("Klienten anlegen"));
	}

	@Test
	public void testClientOverview() {
		createDummyClient();
		Result result = callAction(
				controllers.routes.ref.ClientController.clientOverview(),
				fakeRequest()
						.withSession("username", currentUser.getUserName()));
		assertThat(status(result), is(OK));

		String resultString = contentAsString(result);
		assertTrue(resultString.contains("Klienten"));
		assertTrue(resultString.contains(NAME));
		assertTrue(resultString.contains(PRENAME));
		assertTrue(resultString.contains(BIRTHDAY));
		assertTrue(resultString.contains(STREET));
		assertTrue(resultString.contains(STREET_NUMBER));
		assertTrue(resultString.contains(ZIP_CODE));
		assertTrue(resultString.contains(CITY));
		assertTrue(resultString.contains(PHONE));
	}

	@Test
	public void testShowClient() {
		Client dummyClient = createDummyClientAndReturn();
		Result result = callAction(
				controllers.routes.ref.ClientController.showClient(dummyClient
						.getClientId().toString()),
				fakeRequest()
						.withSession("username", currentUser.getUserName()));
		assertThat(status(result), is(OK));

		String resultString = contentAsString(result);
		assertTrue(resultString.contains("Klienten"));
		assertTrue(resultString.contains(NAME));
		assertTrue(resultString.contains(PRENAME));
		assertTrue(resultString.contains(BIRTHDAY));
		assertTrue(resultString.contains(BIRTHPLACE));
		assertTrue(resultString.contains(BIRTHCOUNTRY));
		assertTrue(resultString.contains(GENDER));
		assertTrue(resultString.contains(FAMILY_STATUS));
		assertTrue(resultString.contains(OCCUPATION));
		assertTrue(resultString.contains(CONFESSION));
		assertTrue(resultString.contains(STREET));
		assertTrue(resultString.contains(STREET_NUMBER));
		assertTrue(resultString.contains(ZIP_CODE));
		assertTrue(resultString.contains(CITY));
		assertTrue(resultString.contains(PHONE));
		assertTrue(resultString.contains(MOBILE));
		assertTrue(resultString.contains(MAIL));
	}

	@Test
	public void testShowListOverview() {
		Client dummyClient = createDummyClientAndReturn();
		Result result = callAction(
				controllers.routes.ref.ClientController.showListOverview(dummyClient
						.getClientId().toString()),
				fakeRequest()
						.withSession("username", currentUser.getUserName()));
		assertThat(status(result), is(OK));
		assertTrue(contentAsString(result).contains("Befreiungen"));
		assertTrue(contentAsString(result).contains("Antr&auml;ge"));
		assertTrue(contentAsString(result).contains("Ausweise"));
		assertTrue(contentAsString(result).contains("Versicherungen"));
		assertTrue(contentAsString(result).contains("Gesundheit"));
		assertTrue(contentAsString(result).contains("Ausl&auml;nder"));
		assertTrue(contentAsString(result).contains("Finanzen"));
		assertTrue(contentAsString(result).contains("Wohnung"));
		assertTrue(contentAsString(result).contains("Sonstiges"));
	}

	@Test
	public void testOnlyLoggedInUsersCanAccessClientController() {
		Result result = callAction(controllers.routes.ref.ClientController
				.clientOverview());
		assertTrue(result.toString().contains("/login"));
	}

	private Result createDummyClient() {
		Map<String, String> data = createFormData();
		return createClientForGivenData(data);
	}

	private Client createDummyClientAndReturn() {
		Map<String, String> data = createFormData();
		createClientForGivenData(data);
		return Client.getAllClients().get(0);
	}

	private Result createClientForGivenData(Map<String, String> data) {
		return callAction(
				controllers.routes.ref.ClientController.createClient(),
				fakeRequest().withFormUrlEncodedBody(data).withSession(
						"username", currentUser.getUserName()));
	}

	private void assertClientData(Client client) {
		assertThat(client.getFormOfAdress(), is(FORM_OF_ADRESS));
		assertThat(client.getTitle(), is(TITLE));
		assertThat(client.getName(), is(NAME));
		assertThat(client.getPrename(), is(PRENAME));
		assertThat(client.getBirthdayAsString(), is(BIRTHDAY));
		assertThat(client.getBirthplace(), is(BIRTHPLACE));
		assertThat(client.getBirthCountry(), is(BIRTHCOUNTRY));
		assertThat(client.getGender(), is(GENDER));
		assertThat(client.getFamilyStatus(), is(FAMILY_STATUS));
		assertThat(client.getOccupation(), is(OCCUPATION));
		assertThat(client.getConfession(), is(CONFESSION));

		assertThat(client.getStreet(), is(STREET));
		assertThat(client.getStreetNumber(), is(STREET_NUMBER));
		assertThat(client.getZipCode(), is(ZIP_CODE));
		assertThat(client.getCity(), is(CITY));
		assertThat(client.getPhones().get(0).getPhoneNumber(), is(PHONE));
		assertThat(client.getMobiles().get(0).getPhoneNumber(), is(MOBILE));
		assertThat(client.getMails().get(0).getMail(), is(MAIL));
	}

	private Map<String, String> createFormData() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("formOfAdress", FORM_OF_ADRESS);
		data.put("title", TITLE);
		data.put("name", NAME);
		data.put("prename", PRENAME);
		data.put("birthday", BIRTHDAY);
		data.put("birthplace", BIRTHPLACE);
		data.put("birthcountry", BIRTHCOUNTRY);
		data.put("gender", GENDER);
		data.put("familyStatus", FAMILY_STATUS);
		data.put("occupation", OCCUPATION);
		data.put("confession", CONFESSION);
		data.put("street", STREET);
		data.put("streetNumber", STREET_NUMBER);
		data.put("zipCode", ZIP_CODE);
		data.put("city", CITY);
		data.put("phone", PHONE);
		data.put("mobile", MOBILE);
		data.put("mail", MAIL);
		data.put("fileReference", FILE_REFERENCE);
		data.put("newCourt", "true");
		data.put("courtName", "Dummy Court");
		data.put("courtStreet", STREET);
		data.put("courtStreetNumber", STREET_NUMBER);
		data.put("courtZipCode", ZIP_CODE);
		data.put("courtCity", CITY);
		data.put("ownIncome", "true");
		data.put("livesAtHome", "true");
		data.put("beginOfAssistance", BIRTHDAY);
		data.put("beginOfAssistanceInThisCompany", BIRTHDAY);
		return data;
	}

	private User createCurrentUser() {
		currentUser = new User("currentUser", "dummyPassword",
				"dummy@email.com", true);
		currentUser.save();
		return currentUser;
	}

}
