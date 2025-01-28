package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.EmployeeWorkHour;
import com.hita.shifttracker.repository.EmployeeWorkHourRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class EmployeeController {



    // get employee dashboard
    @GetMapping("/employee/dashboard")
    public String getEmployeeDashboard(Model model, HttpSession session) {

        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);


        return "/employee/employee-add-work-hour.html";
    }

    // potvrdi vrijeme
    @GetMapping("/employee/workHour/add")
    public String addEmployeeWorkHour(@RequestParam("startTime") String startTime,
                                      @RequestParam("endTime") String endTime,
                                      Model model,
                                      HttpSession session){
        System.out.println("start time: " + startTime);
        System.out.println("end time: " + endTime);

        // split this time per date and work hour;


        return "redirect:/employee/dashboard";
    }

    // get all workhours
    public String getAllWorkHours() {
        return null;
    }
}
