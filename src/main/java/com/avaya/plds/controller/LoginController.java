package com.avaya.plds.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avaya.plds.model.User;
import com.avaya.plds.service.LoginService;

/**
 * Created by tmichels on 8/1/14.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

   /* @RequestMapping(value = "/all.json")
    public @ResponseBody List<String> viewAllTodos(){
        return todoService.allTodos();
    }

    @RequestMapping(value = "/add/{todo}", method = RequestMethod.POST)
    public @ResponseBody void addTodo(@PathVariable("todo") String todo){
        todoService.addTodo(todo);
    }

    @RequestMapping(value = "/delete/{todo}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteTodo(@PathVariable("todo") String todo){
        todoService.deleteTodo(todo);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public @ResponseBody void deleteAllTodo(){
        todoService.deleteAll();
    }

    @RequestMapping(value="/update/{position}/{todo}", method = RequestMethod.PUT)
    public @ResponseBody void updateTodo(@PathVariable("position") String position, @PathVariable("todo") String todo){
        todoService.updateTodo(Integer.valueOf(position), todo);
    }*/

    @RequestMapping("/layout")
    public String getTodoPartialPage() {
        return "login/layout";
    }
    
    
    @RequestMapping("/isloggedin")
    public @ResponseBody String IsUserLoggedIn(HttpServletRequest request) {
    	System.out.println("Inside isloggedin method");
    	
    	User userC=(User)request.getSession().getAttribute("user");
    	if(null!=userC && null!= userC.getHandle() ){
    		return "1";
    	}
    	else{    	
    	return "0";
    	}
    	
    }
    
    @RequestMapping("/secured")
	public @ResponseBody User secured(@RequestBody User user,HttpServletRequest request) {
    	String handle=user.getHandle();
    	User userC=null;
    	//String password=request.getParameter("password");
    	System.out.println("priniting user name"+handle);
    	if(null!=handle){
		request.getSession().setAttribute("user", loginService.getUser(handle.toLowerCase()));
		userC=(User)request.getSession().getAttribute("user");
		System.out.println(user.toString());
    	}
		return userC;
    }
    
    
    @RequestMapping("/secured/getuser")
   	public @ResponseBody User getuser( HttpServletResponse resp,HttpServletRequest request) {
    	User userC=null;
       	if(null!=request.getSession()){
   		userC=(User)request.getSession().getAttribute("user");
   		System.out.println(userC.toString());
       	}
   		return userC;
       }
    
    private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {

		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setValue("");
				cookies[i].setPath("/");
				cookies[i].setDomain(".avaya.com");
				cookies[i].setMaxAge(0);
				resp.addCookie(cookies[i]);
			}
	}
    
    @RequestMapping("/logout")
	public String goToLogout(
			HttpServletRequest request,
			HttpServletResponse response
			) {
		eraseCookie(request, response);
		request.getSession().invalidate();
		
		return "login/layout";
	}
}
