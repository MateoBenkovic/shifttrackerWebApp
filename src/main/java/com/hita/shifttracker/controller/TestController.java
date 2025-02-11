package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private WorkingTimeUserWtCalViewRepository repo;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/test")
    public String test() {

        AppUser user = appUserRepository.findById(1).get();

        System.out.println(user.toString());

        String userCode = appUserRepository.findAppUserCodeById(user.getId());

        List<WorkingTimeUserWtCalView> workingTimes = repo.findRecords(userCode, 2, 2025);

        for(WorkingTimeUserWtCalView wt : workingTimes) {
            System.out.println(wt.toString());
        }

        return "test";
    }
}
