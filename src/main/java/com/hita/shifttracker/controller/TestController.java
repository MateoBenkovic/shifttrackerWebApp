package com.hita.shifttracker.controller;

import com.hita.shifttracker.service.WorkingTimeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class TestController {

    @Autowired
    private WorkingTimeItemService service;

    @GetMapping("/test")
    public String test(Model model) {

        int month = 2;
        int year = 2025;

        Map<String, Object> workingTimeData = service.getFormattedWorkingTimeData(20, 2, 2025);
        model.addAttribute("workingTimeData", workingTimeData);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

//        List<Map<String, Object>> workingTimeItems = service.getWorkingTimeItemByDays(1,2,2025);
//
//        model.addAttribute("wti", workingTimeItems);

        return "test";
    }
}
