package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.*;
import com.hita.shifttracker.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TestController {

    private final AppUserService appUserService;

    public TestController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/test")
    public String test() {

        List<AppUser> appUsers = appUserService.getAllAppUsersWithTeamAndOrganizationUnit();

        for (AppUser appUser : appUsers) {
            System.out.println(appUser.toString());
        }

        return "test";
    }
}
