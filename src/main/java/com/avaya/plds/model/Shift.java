package com.avaya.plds.model;


public class Shift {

	private String name;
	private String shift;
	private String email;
	private String contact;
	private String date;
	

	
	
	
	

	public String getName() {
		return name;
	}






	public void setName(String name) {
		this.name = name;
	}






	public String getShift() {
		return shift;
	}






	public void setShift(String shift) {
		this.shift = shift;
	}






	public String getEmail() {
		return email;
	}






	public void setEmail(String email) {
		this.email = email;
	}






	public String getContact() {
		return contact;
	}






	public void setContact(String contact) {
		this.contact = contact;
	}






	public String getDate() {
		return date;
	}






	public void setDate(String date) {
		this.date = date;
	}



	@Override
	public String toString() {
		return "User [name=" + name + ", shift=" + shift
				+ ", email=" + email + ", contact=" + contact + ", date="
				+ date + "]";
	}
	
}
