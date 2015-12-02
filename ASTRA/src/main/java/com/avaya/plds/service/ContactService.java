package com.avaya.plds.service;


import java.util.List;

import com.avaya.plds.model.ContactVO;

public interface ContactService {
	
	
	public List getContact();
	public int saveContact(ContactVO contactvo);
}
