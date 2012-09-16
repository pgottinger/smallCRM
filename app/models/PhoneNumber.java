package models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class PhoneNumber extends Model {

	@Id
	private final UUID phoneId;

	private final String category;
	private final String phoneNumber;

	public PhoneNumber(String category, String phoneNumber) {
		super();
		this.phoneId = UUID.randomUUID();
		this.category = category;
		this.phoneNumber = phoneNumber;
	}
}
