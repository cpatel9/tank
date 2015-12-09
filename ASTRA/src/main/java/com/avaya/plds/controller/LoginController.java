package com.avaya.plds.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avaya.plds.bean.LoginBean;

import com.avaya.plds.service.LoginService;
@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {	
		
		 ModelAndView model = new ModelAndView("login");
         LoginBean loginBean = new LoginBean();
         model.addObject("loginBean", loginBean);	
         
		return model;
	}
	

	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	 public String executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean")LoginBean loginBean)
	    {
	        ModelAndView model= null;
	        try
	        {
	            boolean isValidUser = loginService.isValidUser(loginBean.getUsername(), loginBean.getPassword());
	            if(isValidUser)
	            {
	                System.out.println("User Login Successful");
	                request.setAttribute("loggedInUser", loginBean.getUsername());	
	                System.out.println(loginBean.getUsername());
	                model = new ModelAndView("/home");
	                
	            }	
	            else
	            {
	                model = new ModelAndView("login");
	                model.addObject("loginBean", loginBean);
	                request.setAttribute("message", "Invalid credentials!!");
	            } 
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        return "redirect:/home";
    }

}
