package models;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Configuration extends Model{
	
	public static final String KEY_INSTALLED="installed";
	
	@Required
	private final String key;
	
	@Required
	private final String value;
	
	public static Model.Finder<String, Configuration> find = new Model.Finder(
			String.class, Configuration.class);

	public Configuration(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}

	public static String getConfigurationValue (String key) {
		Configuration configuration = find.where().eq("key", key).findUnique();
		if(configuration == null) {
			return null;
		}
		return configuration.getValue();
	}
	
	public static boolean isSystemAlreadyInstalled() {
		String configurationValue = getConfigurationValue(KEY_INSTALLED);
		if(configurationValue==null) {
			return false;
		}
		return true;
	}
}
