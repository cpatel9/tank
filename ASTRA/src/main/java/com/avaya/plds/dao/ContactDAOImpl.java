package com.avaya.plds.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.avaya.plds.constants.SQL_CONSTANTS;
import com.avaya.plds.model.ContactVO;


@Repository
public class ContactDAOImpl implements ContactDAO {
	DataSource dataSource;
	Connection con;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	Connection getConnection()throws SQLException{
			return dataSource.getConnection();
		
	}

	@Override
	public List getContact(){
		
		System.out.println("inside ContactDAO.getContact()");
		ContactVO contactvo = null;
		List<ContactVO> contacts = new ArrayList<ContactVO>() ;
		try {
			con = dataSource.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_CONSTANTS.SELECT_CONTACT);
			
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
					
		} catch (SQLException e) {
			System.out.println("Exception occured while creating connection");
			e.printStackTrace();
		}
		finally{
			try {
				if(null!=con)
				   con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return contacts;
	}

	/***
	 * To Save Contact in database
	 */
	@Override
	public int saveContact(ContactVO contactvo) {		
		System.out.println("Inside DAO saveContact ");	
		
		try {
			con = dataSource.getConnection();
			
			Statement st = con.createStatement();
			String query= "INSERT INTO CONTACT (FIRSTNAME, CELL, EMAIL, GENDER, EMPNO) values("+
					"'"+contactvo.getFname()+"', "+
					"'"+contactvo.getCell()+"', "+
					"'"+contactvo.getEmail()+"', "+
					"'"+contactvo.getGender()+"', "+
						contactvo.getEmpno()+
					")";
			System.out.println("query::"+query);
			int recordUpdated = st.executeUpdate(query);			
			
					
		} catch (SQLException e) {
			System.out.println("Exception occured while doing Db operation");
			e.printStackTrace();
		}
		finally{
			try {
				if(null!=con)
				   con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
	
		return 0;
	}
	
	
	
}
