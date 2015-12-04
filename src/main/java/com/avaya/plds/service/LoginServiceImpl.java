package com.avaya.plds.service;

import org.springframework.stereotype.Service;

import com.avaya.plds.model.User;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

	public User getUser(String userName) {
		// TODO Auto-generated method stub
		
		User user=new User();
		user.setFirstName("ravi");
		user.setLastName("lethakula");
		user.setEmail("l.ravichandrareddy@gmail.com");
		user.setHandle(userName);
		user.setPhone("9000002037");
		user.setStatus("Active");
		user.setUserType("admin");
		user.setUpdatedOn("01-JAN-2014");
		
		
		return user;
	}

}
