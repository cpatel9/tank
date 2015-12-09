package com.avaya.plds.controller;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avaya.plds.bean.LoginBean;
import com.avaya.plds.model.EmployeeManager;
import com.avaya.plds.service.ContactService;
import com.avaya.plds.service.ContactServiceImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	ContactService contactService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		
		/*logger.info("Welcome home! The client locale is {}.", locale);		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("contacts",contactService.getContact());*/
		
		 ModelAndView model = new ModelAndView("login");
         LoginBean loginBean = new LoginBean();
         model.addObject("loginBean", loginBean);	
         
		return model;
	}
	


	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	 public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean")LoginBean loginBean)
	    {

	        ModelAndView model= null;
	        try
	        {
	           // boolean isValidUser = loginDelegate.isValidUser(loginBean.getUsername(), loginBean.getPassword());
	            if(true)
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
	        return model;

	    }

	
	
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("contacts",contactService.getContact());
		
		return "home";
	}
	
	@RequestMapping(value = "/addcontact", method = RequestMethod.GET)
	public String addcontact(Locale locale, Model model) {
		logger.info("Success");				
		
		return "contact";
	}
	
}
