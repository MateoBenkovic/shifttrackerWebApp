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

    @Autowired
    EmployeeWorkHourRepository employeeWorkHourRepositoryDB;

    // get employee dashboard
    @GetMapping("/employee/dashboard")
    public String getEmployeeDashboard(Model model) {
        return "/employee/employee-add-work-hour";
    }

    // potvrdi vrijeme
    @GetMapping("/employee/workHour/add")
    public String addEmployeeWorkHour(@RequestParam("startTime") String startTime,
                                      @RequestParam("endTime") String endTime,
                                      Model model,
                                      HttpSession session){

        AppUser currentUser = (AppUser) session.getAttribute("user");

        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);

        int totalHours = (int) Duration.between(start, end).toHours();

        EmployeeWorkHour newEmployeeWorkHour = new EmployeeWorkHour(start, end, totalHours);
        newEmployeeWorkHour.setAppUser(currentUser);
        employeeWorkHourRepositoryDB.save(newEmployeeWorkHour);


        return "redirect:/employee/dashboard";
    }

    // get all workhours
    public String getAllWorkHours() {
        return null;
    }
}
