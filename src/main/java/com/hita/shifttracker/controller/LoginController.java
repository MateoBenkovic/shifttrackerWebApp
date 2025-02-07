package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    private AppUserRepository appUserRepository;

    public LoginController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
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

        List<AppUser> appUsers = appUserRepository.findAll();
        AppUser appUser = null;
        for(AppUser a : appUsers) {
            if(a.getEmail().equals(email) && a.getPassword().equals(password)) {
                appUser = a;
            }
        }
        if (appUser != null) {
            if (appUser.getAppRole().getRoleUserTypeCode().equals("R")) {
                session.setAttribute("appUser", appUser);
                return "redirect:/employee/dashboard";
            } else if (appUser.getAppRole().getRoleUserTypeCode().equals("S")) {
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
