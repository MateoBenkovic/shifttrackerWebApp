package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.repository.AppUserRepository;
import com.hita.shifttracker.service.AppUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    private final AppUserService appUserService;

    public LoginController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/loginProcess")
    public String loginProcess(@RequestParam("email") String email, @RequestParam("password") String password,
                               Model model, HttpSession session) {

        List<AppUserDTO> appUsers = appUserService.getAllAppUsersWithTeamAndOrganizationUnit();
        AppUserDTO appUser = null;
        for(AppUserDTO a : appUsers) {
            if(a.getEmail().equals(email) && a.getPassword().equals(password)) {
                appUser = a;
            }
        }
        if (appUser != null) {
            if (appUser.getRoleUserTypeCode().equals("R")) {
                session.setAttribute("appUser", appUser);
                return "redirect:/employee/workhour/list";
            } else if (appUser.getRoleUserTypeCode().equals("S")) {
                session.setAttribute("appUser", appUser);
                return "redirect:/head_nurse/dashboard";
            }
        }
        model.addAttribute("error", "Netočan email ili lozinka!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("appUser");
        session.invalidate();
        return "login";
    }
}
