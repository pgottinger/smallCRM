package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import controllers.CreateClientForm;

@Entity
public class Client extends Model {

	@Id
	private final UUID clientId;

	private String formOfAdress;
	private String title;
	private final String name;
	private final String prename;

	private final Date birthday;
	private String birthplace;

	private String birthCountry;
	private String gender;
	private String familyStatus;
	private String occupation;
	private String confession;

	private final String street;
	private final String streetNumber;
	private final String zipCode;
	private final String city;

	@OneToMany(cascade = CascadeType.ALL)
	private final List<PhoneNumber> phones = new ArrayList<PhoneNumber>();

	@OneToMany(cascade = CascadeType.ALL)
	private final List<MailAdress> mails = new ArrayList<MailAdress>();

	private final Date createdOn;

	public static Model.Finder<String, Client> findByName = new Model.Finder(
			String.class, Client.class);

	public static Model.Finder<UUID, Client> findById = new Model.Finder(
			UUID.class, Client.class);

	public Client(Form<CreateClientForm> form, MailAdress mail,
			PhoneNumber phone, PhoneNumber mobile) {
		CreateClientForm createClientForm = form.get();

		this.clientId = UUID.randomUUID();
		this.name = createClientForm.name;
		this.prename = createClientForm.prename;
		this.birthday = createClientForm.birthday;
		this.street = createClientForm.street;
		this.streetNumber = createClientForm.streetNumber;
		this.zipCode = createClientForm.zipCode;
		this.city = createClientForm.city;

		phones.add(phone);
		phones.add(mobile);
		mails.add(mail);

		this.createdOn = new Date();
	}

	public static List<Client> getAllClients() {
		return findById.order("name").order("prename").findList();
	}

	public UUID getClientId() {
		return clientId;
	}

	public String getName() {
		return name;
	}

	public String getPrename() {
		return prename;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCity() {
		return city;
	}

	public Collection<MailAdress> getMails() {
		return mails;
	}

	public String getFormOfAdress() {
		return formOfAdress;
	}

	public String getTitle() {
		return title;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public String getGender() {
		return gender;
	}

	public String getFamilyStatus() {
		return familyStatus;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getConfession() {
		return confession;
	}

	public List<PhoneNumber> getPhones() {
		return phones;
	}

	public Date getCreatedOn() {
		return createdOn;
	}
}
