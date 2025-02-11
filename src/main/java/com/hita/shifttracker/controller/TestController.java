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
    private WorkingTimeRepository workingTimeRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/test")
    public String test() {

        // insert into
        WorkingTime workingTime = new WorkingTime();
        workingTime.setDateFrom(LocalDate.of(2023, 1, 1));
        workingTime.setHoursFrom(8);
        workingTime.setDateTo(LocalDate.of(2023, 1, 1));
        workingTime.setHoursTo(16);
        workingTime.setTotalHours(8);
        workingTime.setAppUser(appUserRepository.findById(1).get());
        workingTime.setShiftId(1);

        workingTimeRepository.saveWorkingTime(workingTime.getDateFrom(), workingTime.getHoursFrom(), workingTime.getDateTo(), workingTime.getHoursTo(), workingTime.getTotalHours(), workingTime.getAppUser().getId(), workingTime.getShiftId());

        return "test";
    }
}
