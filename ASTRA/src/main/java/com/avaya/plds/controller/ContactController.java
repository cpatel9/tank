package com.avaya.plds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.avaya.plds.model.ContactVO;
import com.avaya.plds.service.ContactService;
 /***
  * This class is for adding contacts in the plds roaster database
  * @author Chitranjan_Patel
  *
  */
@Controller
@SessionAttributes
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	
 /***
  * 
  * @param contact
  * @param result
  * @return to the home controller
  */
    @RequestMapping(value = "/addContact", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contacts")
                            ContactVO contact, BindingResult result) {
         
        System.out.println("First Name:" + contact.getFname());
        System.out.println(" Gender:"+ contact.getGender());
        contactService.saveContact(contact);
         
        return "redirect:/";
    }
     
    
    /****
     * To forward the contact Add page
     * @return
     */
    @RequestMapping("/contact")
    public ModelAndView showContacts() {
         
    	return new ModelAndView("contact", "contacts", new ContactVO());
    }
}
