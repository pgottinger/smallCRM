package controllers;

import java.util.UUID;

import models.Client;
import models.MailAdress;
import models.PhoneNumber;
import models.PhoneNumber.PhoneCategory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.client.clientOverview;
import views.html.client.showClient;
import views.html.client.showCreateClientForm;
import views.html.client.showListOverview;

public class ClientController extends Controller {

	public static Result showCreateClientForm() {
		return ok(showCreateClientForm.render(form(CreateClientForm.class)));
	}

	public static Result createClient() {
		Form<CreateClientForm> createClientForm = form(CreateClientForm.class)
				.bindFromRequest();
		if (createClientForm.hasErrors()) {
			return badRequest(showCreateClientForm.render(createClientForm));
		} else {
			MailAdress mail = createMailEntity(createClientForm);
			PhoneNumber phoneNumber = createPhoneEntity(createClientForm);
			PhoneNumber mobileNumber = createMobileEntity(createClientForm);
			Client client = new Client(createClientForm, mail, phoneNumber,
					mobileNumber);
			client.save();

			return redirect(routes.ClientController.clientOverview());
		}
	}

	public static Result clientOverview() {
		return ok(clientOverview.render(Client.getAllClients()));
	}

	public static Result showClient(String clientId) {
		UUID clientUUID = UUID.fromString(clientId);
		return ok(showClient.render(Client.getClientById(clientUUID)));
	}

	public static Result showListOverview(String clientId) {
		UUID clientUUID = UUID.fromString(clientId);
		return ok(showListOverview.render(Client.getClientById(clientUUID)));
	}

	private static MailAdress createMailEntity(
			Form<CreateClientForm> createClientForm) {
		String mail = createClientForm.get().getMail();
		if (mail != null) {
			return new MailAdress(mail);
		}
		return null;
	}

	private static PhoneNumber createPhoneEntity(
			Form<CreateClientForm> createClientForm) {
		String phone = createClientForm.get().getPhone();
		if (phone != null) {
			return new PhoneNumber(PhoneCategory.PHONE, phone);
		}
		return null;
	}

	private static PhoneNumber createMobileEntity(
			Form<CreateClientForm> createClientForm) {
		String mobile = createClientForm.get().getMobile();
		if (mobile != null) {
			return new PhoneNumber(PhoneCategory.MOBILE, mobile);
		}
		return null;
	}
}
