package controllers;

import java.util.List;
import java.util.Map;

import models.Client;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.client.showCreateClientForm;
import views.html.client.clientOverview;

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
			Client client = new Client(createClientForm);
			client.save();

			return redirect(routes.Application.index());
		}
	}

	public static Result clientOverview() {
		return ok(clientOverview.render(Client.getAllClients()));
	}

}
