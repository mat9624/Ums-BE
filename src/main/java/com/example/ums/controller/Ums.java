package com.example.ums.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class Ums {

    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "ciao";
    }
}
