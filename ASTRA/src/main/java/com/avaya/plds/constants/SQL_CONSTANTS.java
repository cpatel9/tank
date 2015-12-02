package com.avaya.plds.constants;
/***
 * 
 * @author Chitranjan_Patel
 * All the SQL queries of application
 */
public interface SQL_CONSTANTS {
	/**
	 * To Select all the contact details from database
	 */
	public static final String SELECT_CONTACT = "SELECT FIRSTNAME, CELL, EMAIL, GENDER, EMPNO from CONTACT";
	/**
	 * To insert Data into the DB
	 */
	public static final String INSERT_CONTACT = "SELECT FIRSTNAME, CELL, EMAIL, GENDER, EMPNO from CONTACT";
	/***
	 * To Update existing Contact
	 */
	public static final String Update_CONTACT = "SELECT FIRSTNAME, CELL, EMAIL, GENDER, EMPNO from CONTACT";

}
