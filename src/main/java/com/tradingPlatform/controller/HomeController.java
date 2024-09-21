package com.tradingPlatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "welcome to trading platform";
    }

    @GetMapping("/api")
    public String secureHome(){
        return "This is a secure page";
    }
}
