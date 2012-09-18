package controllers;

import java.util.List;
import java.util.Map;

import models.Client;
import models.MailAdress;
import models.PhoneNumber;
import models.PhoneNumber.PhoneCategory;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.client.clientOverview;
import views.html.client.showCreateClientForm;

public class ClientController extends Controller {

	public static Result showCreateClientForm() {
		return ok(showCreateClientForm.render(form(CreateClientForm.class)));
	}

	public static Result createClient() {
		Form<CreateClientForm> createClientForm = form(CreateClientForm.class)
				.bindFromRequest();
		if (createClientForm.hasErrors()) {
			Map<String, List<ValidationError>> errors = createClientForm
					.errors();
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
		String mobile = createClientForm.get().getPhone();
		if (mobile != null) {
			return new PhoneNumber(PhoneCategory.MOBILE, mobile);
		}
		return null;
	}

	public static Result clientOverview() {
		return ok(clientOverview.render(Client.getAllClients()));
	}

}
