package com.avaya.plds.controller;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




import com.avaya.plds.service.ContactService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	ContactService contactService;
	
		
	
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
