package com.avaya.plds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tmichels on 8/3/14.
 */

@Controller
@RequestMapping("/force")
public class ForceController {

    @RequestMapping("/layout")
    public String layout(){
        return "force/layout";
    }
}
