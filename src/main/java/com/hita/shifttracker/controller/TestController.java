package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.*;
import com.hita.shifttracker.service.DateService;

import com.hita.shifttracker.service.TempSchedulePerEmployeeService;
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
    private WorkingTimeItemRepository repo;

    @GetMapping("/test")
    public String test(Model model) {

        WorkingTimeItem wti = repo.findItemByAppUserIdAndDate(30, LocalDate.of(2025,2,11)).get(0);

        System.out.println(wti.toString());



        return "test";
    }
}
