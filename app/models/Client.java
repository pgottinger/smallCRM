package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import models.PhoneNumber.PhoneCategory;

import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import controllers.CreateClientForm;

@Entity
public class Client extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2724105310533188518L;

	@Id
	private final UUID clientId;

	private final String formOfAdress;
	private final String title;
	private final String name;
	private final String prename;

	private final Date birthday;
	private final String birthplace;

	private final String birthcountry;
	private final String gender;
	private final String familyStatus;
	private final String occupation;
	private final String confession;

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

	public Client(Form<CreateClientForm> clientForm, MailAdress mail,
			PhoneNumber phone, PhoneNumber mobile) {
		CreateClientForm form = clientForm.get();

		this.clientId = UUID.randomUUID();
		this.formOfAdress = form.formOfAdress;
		this.title = form.title;

		this.name = form.name;
		this.prename = form.prename;
		this.birthday = form.birthday;
		this.street = form.street;
		this.streetNumber = form.streetNumber;
		this.zipCode = form.zipCode;
		this.city = form.city;

		this.birthplace = form.birthplace;

		this.birthcountry = form.birthcountry;
		this.gender = form.gender;
		this.familyStatus = form.getFamilyStatus();
		this.occupation = form.occupation;
		this.confession = form.confession;

		phones.add(phone);
		phones.add(mobile);
		mails.add(mail);

		this.createdOn = new Date();
	}

	public static List<Client> getAllClients() {
		return findById.order("name").order("prename").findList();
	}

	public static Client getClientById(UUID clientId) {
		return findById.where().eq("clientId", clientId).findUnique();
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

	public String getBirthdayAsString() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.format(birthday);
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

	public List<MailAdress> getMails() {
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
		return birthcountry;
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
		return getPhoneNumbersOfCategory(PhoneCategory.PHONE);
	}

	public List<PhoneNumber> getMobiles() {
		return getPhoneNumbersOfCategory(PhoneCategory.MOBILE);
	}

	private List<PhoneNumber> getPhoneNumbersOfCategory(PhoneCategory category) {
		List<PhoneNumber> phoneList = new ArrayList<PhoneNumber>();
		for (PhoneNumber phoneNumber : phones) {
			if (phoneNumber.getCategory() == category) {
				phoneList.add(phoneNumber);
			}
		}
		return phoneList;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

}
