package com.hita.shifttracker.controller;

import com.hita.shifttracker.service.WorkingTimeItemService;
import com.hita.shifttracker.service.WorkingTimeService;
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
    private WorkingTimeService service;

    @GetMapping("/test")
    public String test(Model model) {

        LocalDate dateFrom = LocalDate.of(2025,2,24);
        service.deleteWorkingTimeByAppUserIdAndDateFrom(20, dateFrom);

        return "test";
    }
}
