package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.dto.CompanyDTO;
import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.*;
import com.hita.shifttracker.service.AppUserService;
import com.hita.shifttracker.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @GetMapping("/test")
    public String test() {


        workingTimeRepository.insertOrUpdateWorkingTime(2, LocalDate.parse("2025-02-12"), 19,
                LocalDate.parse("2025-02-13"), 7, 12, 2);

        return "test";
    }
}
