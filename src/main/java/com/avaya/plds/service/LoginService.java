package com.avaya.plds.service;

import java.util.List;

import com.avaya.plds.model.User;


public interface LoginService {
    public User getUser(String userName);
}
