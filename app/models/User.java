package models;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.Form;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http.Context;
import controllers.Installation.InstallationForm;
import controllers.UserAdmin.CreateUserForm;

@Entity
public class User extends Model {

	@Id
	@Constraints.Required
	private final UUID userId;

	@Constraints.Required
	@Formats.NonEmpty
	private final String userName;

	@Constraints.Required
	@Formats.NonEmpty
	private final String userPassword;

	@Constraints.Required
	private final boolean isAdmin;

	public static Model.Finder<String, User> find = new Model.Finder(
			String.class, User.class);

	public User(String userName, String userPassword, boolean isAdmin) {
		super();
		this.userId = UUID.randomUUID();
		this.userName = userName;
		this.userPassword = hashPassword(userPassword);
		this.isAdmin = isAdmin;
	}

	public User(Form<CreateUserForm> createUserForm) {
		this(createUserForm.field("username").value(), createUserForm.field(
				"password").value(), new Boolean(createUserForm.field(
				"username").value()));

	}

	public static User createUserFromInstallationForm(
			Form<InstallationForm> installationForm) {
		return new User(installationForm.field("username").value(),
				installationForm.field("password").value(), true);

	}

	public static User authenticate(String userName, String userPassword) {
		return find.where().eq("userName", userName)
				.eq("userPassword", hashPassword(userPassword)).findUnique();

	}

	public static User getUserByName(String username) {
		return find.where().eq("userName", username).findUnique();
	}

	public static int getNumberOfUsers() {
		return find.getMaxRows();
	}

	public static User getLoggedInUser() {
		return User.getUserByName(Context.current().session().get("username"));
	}

	private static String hashPassword(String plain) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] hash = new byte[40];
			md.update(plain.getBytes("UTF-8"), 0, plain.length());
			hash = md.digest();

			BigInteger number = new BigInteger(1, hash);
			return number.toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public String getUserName() {
		return userName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public UUID getUserId() {
		return userId;
	}
}
