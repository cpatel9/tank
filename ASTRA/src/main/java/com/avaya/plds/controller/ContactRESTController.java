package com.avaya.plds.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import com.avaya.plds.model.ContactVO;
import com.avaya.plds.model.EmployeeListVO;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ContactRESTController {

    @RequestMapping(value = "/employees")
    public List getAllEmployees()
    {
        //EmployeeListVO employees = new EmployeeListVO();
    	ArrayList employees = new ArrayList();
          
        ContactVO empOne = new ContactVO(1,"Lokesh","Gupta","howtodoinjava@gmail.com");
        ContactVO empTwo = new ContactVO(2,"Amit","Singhal","asinghal@yahoo.com");
        ContactVO empThree = new ContactVO(3,"Kirti","Mishra","kmishra@gmail.com");
          
          
        employees.add(empOne);
        employees.add(empTwo);
        employees.add(empThree);
          
        return employees;
    }
      
    @RequestMapping(value = "/employees/{id}")
    public ResponseEntity<ContactVO> getEmployeeById (@PathVariable("id") int id)
    {
        if (id <= 3) {
            ContactVO employee = new ContactVO(1,"Lokesh","Gupta","howtodoinjava@gmail.com");
            return new ResponseEntity<ContactVO>(employee, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
