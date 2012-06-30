package models;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Relative extends Model {

	@Id
	private final long id;

	private final String name;
	private final String prename;
	private final Date birthday;
	private final String street;
	private final String streetNumber;
	private final String zipCode;
	private final Collection<PhoneNumber> phones;
	private final Collection<MailAdress> mails;

	private final String diseases;
	private final String biography;

	private final Date custodyStart;
	private final String custodyDemandedBy;
	private final Date custodyRequestDate;

	private final Date createdOn;

	public Relative(long id, String name, String prename, Date birthday,
			String street, String streetNumber, String zipCode,
			Collection<PhoneNumber> phones, Collection<MailAdress> mails,
			String diseases, String biography, Date custodyStart,
			String custodyDemandedBy, Date custodyRequestDate, Date createdOn) {
		this.id = id;
		this.name = name;
		this.prename = prename;
		this.birthday = birthday;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.phones = phones;
		this.mails = mails;
		this.diseases = diseases;
		this.biography = biography;
		this.custodyStart = custodyStart;
		this.custodyDemandedBy = custodyDemandedBy;
		this.custodyRequestDate = custodyRequestDate;
		this.createdOn = createdOn;
	}

}
