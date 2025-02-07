package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
public class EmployeeController {


    // glavna sestra vidi listu svih zaposlenika
    @GetMapping("/employee/dashboard")
    public String getDashboard(HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        return "redirect:/employee/list";
    }



}
