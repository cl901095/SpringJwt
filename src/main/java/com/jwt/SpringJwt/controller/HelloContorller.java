package com.jwt.SpringJwt.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContorller {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "welcome to homepage"+request.getSession().getId();

    }
}


