package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class MailAdress extends Model {

	@Id
	private final long id;

	private final String mailAdress;

	public MailAdress(long id, String mailAdress) {
		super();
		this.id = id;
		this.mailAdress = mailAdress;
	}
}
