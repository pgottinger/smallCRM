package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Court extends Model {

	@Id
	private final UUID courtId;

	@Required
	private final String courtName;

	@Required
	private final String courtStreet;

	@Required
	private final String courtStreetNumber;

	@Required
	private final String courtZipCode;

	@Required
	private final String courtCity;

	public static Model.Finder<UUID, Court> findById = new Model.Finder(
			UUID.class, Court.class);

	public Court(final String courtName, final String courtStreet,
			final String courtStreeNumber, final String courtZipCode,
			final String courtCity) {
		courtId = UUID.randomUUID();

		this.courtName = courtName;
		this.courtStreet = courtStreet;
		this.courtStreetNumber = courtStreeNumber;
		this.courtZipCode = courtZipCode;
		this.courtCity = courtCity;
	}

	public static List<Court> getAllCourts() {
		return findById.order("courtName").findList();
	}

	public static Map<String, String> list() {
		Map<String, String> map = new HashMap<String, String>();
		for (Court court : getAllCourts()) {
			map.put(court.getCourtId().toString(), court.courtName);
		}

		return map;
	}

	public static Court findCourtById(String id) {
		UUID courtId = UUID.fromString(id);
		return findById.where().eq("courtId", courtId).findUnique();
	}

	public UUID getCourtId() {
		return courtId;
	}

	public String getCourtName() {
		return courtName;
	}

	public String getCourtStreet() {
		return courtStreet;
	}

	public String getCourtStreetNumber() {
		return courtStreetNumber;
	}

	public String getCourtZipCode() {
		return courtZipCode;
	}

	public String getCourtCity() {
		return courtCity;
	}

}
