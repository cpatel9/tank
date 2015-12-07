package com.avaya.plds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/tasks")
public class TaskController {

    @RequestMapping("/layout")
    public String layout(){
        return "tasks/layout";
    }

    /*@RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(){

    }*/
}
