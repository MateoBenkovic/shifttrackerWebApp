package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.PeriodRepository;
import com.hita.shifttracker.repository.TempSchedulePerEmployeeRepository;
import com.hita.shifttracker.repository.WorkingOvertimeRepository;
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
    private WorkingOvertimeRepository repo;

    @GetMapping("/test")
    public String test(Model model) {

        List<WorkingOvertime> workingOvertimes = repo.findAll();
        for(WorkingOvertime wo : workingOvertimes) {
            System.out.println(wo.toString());
        }

        return "test";
    }
}
