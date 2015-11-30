package com.avaya.plds.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.plds.dao.ContactDAO;

@Service
public class ContactServiceImpl implements ContactService{
 @Autowired ContactDAO contactdao;	
	
	@Override
	public List getContact() {
		System.out.println("Inside Service getContact..");
		
		return contactdao.getContact();
	}

}
