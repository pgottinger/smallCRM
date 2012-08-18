package controllers;

import java.util.Date;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;

public class CreateClientForm {

	public String name;
	public String prename;

	@Required
	@Formats.DateTime(pattern = "dd.MM.yyyy")
	public Date birthday;
	public String street;
	public String streetNumber;
	public String zipCode;
	public String city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public String getStreet() {
		return street;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
