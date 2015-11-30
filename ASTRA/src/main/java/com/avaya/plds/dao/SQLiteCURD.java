package com.avaya.plds.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.avaya.plds.model.ContactVO;

public class SQLiteCURD {
/**
 * Get connection
 * @param args
 */
	
	
	public static void main(String[] args) {	
			List<ContactVO> contacts = new ArrayList<ContactVO>() ;
		    Connection c = null;
		    ContactVO contactvo = null;
		    try {	
		   
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:C:/Users/chitranjan_patel/Documents/WORKSPACE-sts-3.7.1.RELEASE/PLDSTIER3.db");
		      Statement st = c.createStatement();
		      ResultSet rs = st.executeQuery("select FIRSTNAME,CELL,EMAIL,GENDER,EMPNO from CONTACT");
		      JSONArray jsonArray = new JSONArray();
		      while(rs.next())
		      {
		    	  JSONObject jsonObject = new JSONObject();
		    	  System.out.println(rs.getString(1) + "||" + rs.getString(2)
							+ "||" + rs.getString(3) + "||" + rs.getString(4)
							+ "||" + rs.getString(5));
					contactvo = new ContactVO();
					contactvo.setFname(rs.getString(1));
					jsonObject.put("Fname", rs.getString(1));
					contactvo.setCell(rs.getString(2));
					jsonObject.put("Cell", rs.getString(2));
					contactvo.setEmail(rs.getString(3));
					jsonObject.put("Email", rs.getString(3));
					contactvo.setGender(rs.getString(4));
					jsonObject.put("Gender", rs.getString(4));
					contactvo.setEmpno(rs.getInt(5));
					jsonObject.put("Empno", rs.getString(5));
					contacts.add(contactvo);	
					jsonArray.add(jsonObject);
				}								
				
				
				System.out.println(jsonArray.toJSONString());
				
				
		      System.out.println("Opened database successfully");
		    } catch ( Exception e ) {
		    	
		    	System.out.println("some erorr occured in connection");
		    	e.printStackTrace();
		    }finally{
				  try{
					  if(null!=c){
						  c.close();
					  }
				  }catch(Exception e){
				  e.printStackTrace();
				  }
		    	}
		    
		  }
			


}
