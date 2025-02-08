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
    private WorkingTimeUserWtCalViewRepository workingTimeUserWtCalViewRepository;

    @GetMapping("/test")
    public String test() {

        List<WorkingTimeUserWtCalView> workingTimeUserWtCalViews = workingTimeUserWtCalViewRepository.findAll();

        for(WorkingTimeUserWtCalView workingTimeUserWtCalView : workingTimeUserWtCalViews) {
            System.out.println(workingTimeUserWtCalView.toString());
        }

        return "test";
    }
}
