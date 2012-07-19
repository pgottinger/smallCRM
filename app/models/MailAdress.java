package models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class MailAdress extends Model {

	@Id
	private final UUID mailId;

	private final String mailAdress;

	public MailAdress(UUID mailId, String mailAdress) {
		super();
		this.mailId = mailId;
		this.mailAdress = mailAdress;
	}
}
