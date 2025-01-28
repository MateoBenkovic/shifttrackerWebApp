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

    public LoginController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    // show login.html form
    @GetMapping("/login")
    public String getLogin() {
        return "/login";
    }

    // login.html process
    @GetMapping("/loginProcess")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model,
                               HttpSession session) {
        AppUser appUser = null;

        for (AppUser u : appUserRepository.findAll()) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                appUser = u;
                System.out.println("user: " + appUser.toString());
            }
        }

        if (appUser == null) {
            model.addAttribute("loginMessage", "Netočan email ili lozinka!");
            return "login.html";
        } else if (appUser.getAppRole().getId() == 1) {
            session.setAttribute("appUser", appUser);
            return "redirect:/employee/dashboard";
        } else if (appUser.getAppRole().getId() == 2) {
            session.setAttribute("appUser", appUser);
            return "redirect:/head-nurse/dashboard";
        }
        return "login.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("appUser");
        session.invalidate();
        return "/login.html";
    }
}
