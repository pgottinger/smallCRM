package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

enum PhoneCategory {
	PRIVATE, MOBILE, OFFICE
}

@Entity
public class PhoneNumber extends Model {

	@Id
	private final long id;

	private final PhoneCategory category;
	private final String phoneNumber;

	public PhoneNumber(long id, PhoneCategory category, String phoneNumber) {
		super();
		this.id = id;
		this.category = category;
		this.phoneNumber = phoneNumber;
	}
}
