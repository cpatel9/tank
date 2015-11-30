package com.avaya.plds.model;

public class ContactVO {
	private int empno;
	private String fname;
	private String cell;
	private String email;
	private String gender;
	
	public ContactVO(int i, String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}
	public ContactVO() {
		// TODO Auto-generated constructor stub
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

}
