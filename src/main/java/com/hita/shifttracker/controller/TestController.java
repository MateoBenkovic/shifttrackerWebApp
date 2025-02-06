package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/")
    public String test() {

        List<AppUser> appUsers = appUserRepository.findAll();

        for(AppUser appUser : appUsers) {
            System.out.println(appUser.toString());
        }


        return "test";
    }
}
