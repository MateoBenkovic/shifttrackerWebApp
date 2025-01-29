package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.EmployeeWorkHour;
import com.hita.shifttracker.repository.EmployeeWorkHourRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class EmployeeController {

    private EmployeeWorkHourRepository employeeWorkHourRepository;

    public EmployeeController(EmployeeWorkHourRepository employeeWorkHourRepository) {
        this.employeeWorkHourRepository = employeeWorkHourRepository;
    }

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

        AppUser appUser = (AppUser) session.getAttribute("appUser");


        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        int totalHours = (int) Duration.between(start, end).toHours();
        EmployeeWorkHour employeeWorkHour = new EmployeeWorkHour(start, end, totalHours, appUser);

        int MORNING_START = 6;
        int MORNING_END = 14;
        int NOON_START = 14;
        int NOON_END = 22;
        int NIGHT_START = 22;
        int NIGHT_END = 6; // Night shift ends at 6 AM

        int workdayMorning = 0, workdayNoon = 0, workdayNight = 0;
        int saturdayMorning = 0, saturdayNoon = 0, saturdayNight = 0;
        int sundayMorning = 0, sundayNoon = 0, sundayNight = 0;

        LocalDateTime current = start;
        while (current.isBefore(end)) {
            // Get the current hour and day of the week
            int currentHour = current.getHour();
            DayOfWeek dayOfWeek = current.getDayOfWeek();

            // Check the shift for the current hour and increment the corresponding shift counter
            if (currentHour >= MORNING_START && currentHour < MORNING_END) {
                if (dayOfWeek == DayOfWeek.SATURDAY) {
                    saturdayMorning++;
                } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                    sundayMorning++;
                } else {
                    workdayMorning++;
                }
            } else if (currentHour >= NOON_START && currentHour < NOON_END) {
                if (dayOfWeek == DayOfWeek.SATURDAY) {
                    saturdayNoon++;
                } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                    sundayNoon++;
                } else {
                    workdayNoon++;
                }
            } else {
                if (dayOfWeek == DayOfWeek.SATURDAY) {
                    saturdayNight++;
                } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                    sundayNight++;
                } else {
                    workdayNight++;
                }
            }

            // Move to the next hour
            current = current.plusHours(1);
        }

        System.out.println("Workday: " + workdayMorning + " " + workdayNoon + " " + workdayNight);
        System.out.println("Sutarday: " + saturdayMorning + " " + sundayNoon + " " + saturdayNight);
        System.out.println("Sunday: " + sundayMorning + " " + sundayNoon + " " + sundayNight);

        model.addAttribute("workdayMorning", workdayMorning);
        model.addAttribute("workdayNoon", workdayNoon);
        model.addAttribute("workdayNight", workdayNight);

        model.addAttribute("saturdayMorning", saturdayMorning);
        model.addAttribute("saturdayNoon", saturdayNoon);
        model.addAttribute("saturdayNight", saturdayNight);

        model.addAttribute("sundayMorning", sundayMorning);
        model.addAttribute("sundayNoon", sundayNoon);
        model.addAttribute("sundayNight", sundayNight);

        employeeWorkHourRepository.save(employeeWorkHour);


        return "/rezultat.html";
    }

    @GetMapping("/goToMain")
    public String goToMain(){
        return "redirect:/employee/dashboard";
    }

    // get all workhours
    public String getAllWorkHours() {
        return null;
    }
}
