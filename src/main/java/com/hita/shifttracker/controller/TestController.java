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


    @GetMapping("/test")
    public String test() {


        return "test";
    }
}
