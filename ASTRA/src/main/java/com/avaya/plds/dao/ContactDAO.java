package com.avaya.plds.dao;

import java.util.List;

import com.avaya.plds.model.ContactVO;

public interface ContactDAO {
	
	public List getContact();
	public int saveContact(ContactVO contactvo);
}
