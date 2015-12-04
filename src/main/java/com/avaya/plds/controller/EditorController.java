package com.avaya.plds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tmichels on 8/3/14.
 */

@Controller
@RequestMapping("/editor")
public class EditorController {

    @RequestMapping("/layout")
    public String layout(){
        return "editor/layout";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(){

    }
}
