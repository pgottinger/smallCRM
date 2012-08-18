package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.Form;
import play.db.ebean.Model;
import controllers.CreateClientForm;

@Entity
public class Client extends Model {

	@Id
	private final UUID clientId;

	private final String name;
	private final String prename;
	private final Date birthday;
	private final String street;
	private final String streetNumber;
	private final String zipCode;
	private final String city;

	// @OneToMany
	// private final Collection<PhoneNumber> phones;
	//
	// @OneToMany
	// private final Collection<MailAdress> mails;

	private final String diseases;
	private final String biography;

	private final Date custodyStart;
	private final String custodyDemandedBy;
	private final Date custodyRequestDate;

	private final Date createdOn;

	public static Model.Finder<String, Client> findByName = new Model.Finder(
			String.class, Client.class);

	public static Model.Finder<UUID, Client> findById = new Model.Finder(
			UUID.class, Client.class);

	public Client(String name, String prename, Date birthday, String street,
			String streetNumber, String zipCode, String city,
			Collection<PhoneNumber> phones, Collection<MailAdress> mails,
			String diseases, String biography, Date custodyStart,
			String custodyDemandedBy, Date custodyRequestDate, Date createdOn) {
		this.clientId = UUID.randomUUID();
		this.name = name;
		this.prename = prename;
		this.birthday = birthday;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
		// this.phones = phones;
		// this.mails = mails;
		this.diseases = diseases;
		this.biography = biography;
		this.custodyStart = custodyStart;
		this.custodyDemandedBy = custodyDemandedBy;
		this.custodyRequestDate = custodyRequestDate;
		this.createdOn = createdOn;
	}

	public Client(Form<CreateClientForm> form) {
		CreateClientForm createClientForm = form.get();

		this.clientId = UUID.randomUUID();
		this.name = createClientForm.name;
		this.prename = createClientForm.prename;
		this.birthday = createClientForm.birthday;
		this.street = createClientForm.street;
		this.streetNumber = createClientForm.streetNumber;
		this.zipCode = createClientForm.zipCode;
		this.city = createClientForm.city;

		// PhoneNumber phoneNumber = new PhoneNumber(PhoneCategory.PRIVATE,
		// "04711 2234");
		// phoneNumber.save();
		// Collection<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
		// phoneNumbers.add(phoneNumber);
		//
		// MailAdress mail = new MailAdress("peter@test.de");
		// mail.save();
		// Collection<MailAdress> mails = new ArrayList<MailAdress>();
		// mails.add(mail);
		//
		// this.phones = phoneNumbers;
		// this.mails = mails;
		this.diseases = "empty";
		this.biography = "empty";
		this.custodyStart = new Date();
		this.custodyDemandedBy = "empty";
		this.custodyRequestDate = new Date();
		this.createdOn = new Date();
	}

	public static List<Client> getAllClients() {
		return findById.all();
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

	// public Collection<PhoneNumber> getPhones() {
	// return phones;
	// }
	//
	// public Collection<MailAdress> getMails() {
	// return mails;
	// }

	public String getDiseases() {
		return diseases;
	}

	public String getBiography() {
		return biography;
	}

	public Date getCustodyStart() {
		return custodyStart;
	}

	public String getCustodyDemandedBy() {
		return custodyDemandedBy;
	}

	public Date getCustodyRequestDate() {
		return custodyRequestDate;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

}
