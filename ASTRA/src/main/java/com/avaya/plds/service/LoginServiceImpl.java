package com.avaya.plds.service;

import org.springframework.stereotype.Service;

@Service

public class LoginServiceImpl implements LoginService{

	@Override
	public boolean isValidUser(String username, String password) {
	
		boolean isValid= false;
		if(username.equals(password)){
			isValid=true;
		}
		System.out.println(isValid);
		return isValid;
	}

}
