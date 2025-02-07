package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HeadNurseController {

    private AppUserRepository appUserRepository;

    public HeadNurseController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("head_nurse/dashboard")
    public String getHeadNurseDashboard(HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        return "redirect:/head_nurse/employee/list";
    }

    @GetMapping("/head_nurse/employee/list")
    public String getEmployeeList(Model model, HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);


        List<AppUser> employees = appUserRepository.findAllByAppRoleId(1);
        model.addAttribute("employees", employees);

        for (AppUser employee : employees) {
            System.out.println(employee.toString());
        }

        return "head_nurse_employee_list";
    }
}
