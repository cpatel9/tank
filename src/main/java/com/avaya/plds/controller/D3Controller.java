package com.avaya.plds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tmichels on 8/4/14.
 */

@Controller
@RequestMapping("/d3")
public class D3Controller {

    @RequestMapping("/layout")
    public String layout(){
        return "d3/layout";
    }

}
