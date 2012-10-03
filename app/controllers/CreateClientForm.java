package controllers;

import java.util.Date;

import models.Court;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;

public class CreateClientForm {

	@Required
	public String formOfAdress;

	public String title;

	@Required
	public String name;

	@Required
	public String prename;

	@Required
	@Formats.DateTime(pattern = "dd.MM.yyyy")
	public Date birthday;

	public String birthplace;

	public String birthcountry;

	@Required
	public String gender;

	@Required
	public String familyStatus;

	@Required
	public String occupation;

	public String confession;

	@Required
	public String street;

	@Required
	public String streetNumber;

	@Required
	public String zipCode;

	@Required
	public String city;

	public String mail;

	public String phone;

	public String mobile;

	public String court;

	public boolean newCourt;

	public String courtName;

	public String courtStreet;

	public String courtStreetNumber;

	public String courtZipCode;

	public String courtCity;

	@Required
	public String fileReference;

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

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

	public String getFormOfAdress() {
		return formOfAdress;
	}

	public void setFormOfAdress(String formOfAdress) {
		this.formOfAdress = formOfAdress;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleForForm() {
		if (title == null) {
			return "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getBirthCountry() {
		return birthcountry;
	}

	public void setBirthcountry(String birthCountry) {
		this.birthcountry = birthCountry;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(String familyStatus) {
		this.familyStatus = familyStatus;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getConfession() {
		return confession;
	}

	public void setConfession(String confession) {
		this.confession = confession;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public boolean isNewCourt() {
		return newCourt;
	}

	public void setNewCourt(boolean newCourt) {
		this.newCourt = newCourt;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getCourtStreet() {
		return courtStreet;
	}

	public void setCourtStreet(String courtStreet) {
		this.courtStreet = courtStreet;
	}

	public String getCourtStreetNumber() {
		return courtStreetNumber;
	}

	public void setCourtStreetNumber(String courtStreetNumber) {
		this.courtStreetNumber = courtStreetNumber;
	}

	public String getCourtZipCode() {
		return courtZipCode;
	}

	public void setCourtZipCode(String courtZipCode) {
		this.courtZipCode = courtZipCode;
	}

	public String getCourtCity() {
		return courtCity;
	}

	public void setCourtCity(String courtCity) {
		this.courtCity = courtCity;
	}

	public String getBirthcountry() {
		return birthcountry;
	}

	public String getFileReference() {
		return fileReference;
	}

	public void setFileReference(String fileReference) {
		this.fileReference = fileReference;
	}

}
