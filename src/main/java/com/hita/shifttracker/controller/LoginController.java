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


    // show login form
    @GetMapping("/login")
    public String getLogin() {
        return "/login";
    }

    // login process
    @GetMapping("/loginProcess")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model,
                               HttpSession session) {
        AppUser user = null;

        for (AppUser u : appUserRepository.findAll()) {
            System.out.println("Users: " + u);
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                user = u;
            }
        }
        if (user == null) {
            model.addAttribute("loginMessage", "Netočan email ili lozinka!");
            return "login";
        } else {
            System.out.println("User logged in: " + user);
            if (user.getAppRole().getId() == 1) {
                session.setAttribute("user", user);
                return "redirect:/employee/dashboard";
            } else if (user.getAppRole().getId() == 3) {
                session.setAttribute("user", user);
                return "redirect:/employeeList";
            }
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
