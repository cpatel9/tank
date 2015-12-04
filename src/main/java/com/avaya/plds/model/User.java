package com.avaya.plds.model;


public class User {

	private String handle;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String status;
	private String userType;
	private String updatedOn;
	
	
	public String getHandle() {
		return handle;
	}



	public void setHandle(String handle) {
		this.handle = handle;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}



	public String getUpdatedOn() {
		return updatedOn;
	}



	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	

	@Override
	public String toString() {
		return "User [handle=" + handle + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", phone="
				+ phone +
				", status=" + status
				+ ", userType=" + userType
				+ ", updatedOn=" + updatedOn  + "]";
	}
	
}
