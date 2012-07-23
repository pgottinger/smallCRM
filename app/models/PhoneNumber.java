package models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

enum PhoneCategory {
	PRIVATE, MOBILE, OFFICE
}

@Entity
public class PhoneNumber extends Model {

	@Id
	private final UUID phoneId;

	private final PhoneCategory category;
	private final String phoneNumber;

	public PhoneNumber(PhoneCategory category, String phoneNumber) {
		super();
		this.phoneId = UUID.randomUUID();
		this.category = category;
		this.phoneNumber = phoneNumber;
	}
}
