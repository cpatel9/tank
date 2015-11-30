package com.avaya.plds.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ContactDAOImpl implements ContactDAO {
	
	public List getContact(){
		
		System.out.println("inside ContactDAO.getContact()");
		return null;
	}
	
}
