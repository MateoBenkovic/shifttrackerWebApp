package com.hita.shifttracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    // show login form
    @GetMapping("/login")
    public String getLogin() {
        return "/login";
    }

    // login process
    @PostMapping("/login/process")
    public String processLogin() {
        return "null";
    }
}
