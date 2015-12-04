package com.avaya.plds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tmichels on 8/4/14.
 */

@Controller
@RequestMapping("/tabs")
public class TabsController {

    @RequestMapping("/layout")
    public String layout(){
        return "tabs/layout";
    }
}
