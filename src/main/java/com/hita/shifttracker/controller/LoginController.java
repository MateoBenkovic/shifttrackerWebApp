package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private AppUserRepository appUserRepository;

    public LoginController (AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // show login form
    @GetMapping("/login")
    public String getLogin() {
        return "/login";
    }

    // login process
    @GetMapping("/login/process")
    public String processLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        AppUser appUser = null;
        for (AppUser u : appUserRepository.findAll()) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                appUser = u;
            }
        }
        if (appUser == null) {
            model.addAttribute("loginMessage", "Netočan email ili lozinka!");
            return "/login";
        } else {
            System.out.println("User loged in: " + appUser);
            if (appUser.getAppRole().getId() == 2) {
                session.setAttribute("zaposlenik", appUser);
                return "redirect:/employee/dashboard";
            } else if (appUser.getAppRole().getId() == 1) {
                session.setAttribute("koordinator", appUser);
                return "redirect:/headnurse/dashboard";
            } else {
                model.addAttribute("loginMessage", "Netočan email ili lozinka!");
            }

        }
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
