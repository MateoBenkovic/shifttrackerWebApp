package com.hita.shifttracker.controller;

import org.springframework.stereotype.Controller;

@Controller
public class EmployeeController {

    // get employee dashboard
    public String getEmployeeDashboard() {
        return "/employee/dashboard"; //???
    }

    // potvrdi vrijeme
    public String addEmployeeWorkHour(){
        return "";
    }

    // get all workhours
    public String getAllWorkHours() {
        return null;
    }
}
