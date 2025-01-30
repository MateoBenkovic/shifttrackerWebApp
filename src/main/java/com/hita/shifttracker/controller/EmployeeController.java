package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.EmployeeWorkHour;
import com.hita.shifttracker.repository.EmployeeWorkHourRepository;
import com.hita.shifttracker.repository.SchedulePerMonthRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Controller
public class EmployeeController {

    private EmployeeWorkHourRepository employeeWorkHourRepository;
    private SchedulePerMonthRepository schedulePerMonthRepository;

    public EmployeeController(EmployeeWorkHourRepository employeeWorkHourRepository, SchedulePerMonthRepository schedulePerMonthRepository) {
        this.employeeWorkHourRepository = employeeWorkHourRepository;
        this.schedulePerMonthRepository = schedulePerMonthRepository;
    }

    // get employee dashboard
    @GetMapping("/employee/dashboard")
    public String getEmployeeDashboard(Model model, HttpSession session) {

        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        // get workhour list
//        List<SchedulePerMonth> workHours = schedulePerMonthRepository.findByAppUserId(appUser.getId());
//        model.addAttribute("workHours", workHours);


        return "/employee/employee-add-work-hour.html";
    }

    private int calculateWorkedHours(LocalDateTime start, LocalDateTime shiftStart, LocalDateTime shiftEnd, LocalDateTime currentDate) {
        // Adjust the start time if it's before the shift start
        if (start.isBefore(shiftStart)) {
            start = shiftStart;
        }

        // If the start time is after the shift end, return 0 hours for this shift
        if (start.isAfter(shiftEnd)) {
            return 0; // No work during this shift
        }

        // If we are working on the second day (like for the night shift from the previous day), we handle it
        if (currentDate.isBefore(shiftStart)) {
            shiftStart = shiftStart.plusDays(1);  // Account for crossing into the next day
            shiftEnd = shiftEnd.plusDays(1);
        }

        // Calculate worked time in hours, rounded down to the nearest whole hour
        long workedHours = Math.min(ChronoUnit.HOURS.between(start, shiftEnd), ChronoUnit.HOURS.between(shiftStart, shiftEnd));

        // Return the number of worked hours
        return (int) workedHours;

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

        Duration totalDuration = Duration.between(start, end);

        // Initialize counters for each segment
        long morning = 0;
        long nonMorning = 0;
        long night = 0;

        // For the morning segment (6:00 AM to 2:00 PM)
        LocalDateTime morningStart = start.withHour(6).withMinute(0);
        LocalDateTime morningEnd = start.withHour(14).withMinute(0);
        if (start.isBefore(morningEnd) && end.isAfter(morningStart)) {
            // Calculate overlap with morning segment
            LocalDateTime overlapStart = start.isBefore(morningStart) ? morningStart : start;
            LocalDateTime overlapEnd = end.isAfter(morningEnd) ? morningEnd : end;
            morning = Duration.between(overlapStart, overlapEnd).toHours();
        }

        // For the non-morning segment (2:00 PM to 10:00 PM)
        LocalDateTime nonMorningStart = start.withHour(14).withMinute(0);
        LocalDateTime nonMorningEnd = start.withHour(22).withMinute(0);
        if (start.isBefore(nonMorningEnd) && end.isAfter(nonMorningStart)) {
            // Calculate overlap with non-morning segment
            LocalDateTime overlapStart = start.isBefore(nonMorningStart) ? nonMorningStart : start;
            LocalDateTime overlapEnd = end.isAfter(nonMorningEnd) ? nonMorningEnd : end;
            nonMorning = Duration.between(overlapStart, overlapEnd).toHours();
        }

        // For the night segment (10:00 PM to 6:00 AM next day)
        // If the start time is after 10:00 PM, adjust the calculation
        LocalDateTime nightStart = start.withHour(22).withMinute(0);
        LocalDateTime nightEnd = start.plusDays(1).withHour(6).withMinute(0); // 6 AM the next day
        if (start.isBefore(nightEnd) && end.isAfter(nightStart)) {
            // Calculate overlap with night segment
            LocalDateTime overlapStart = start.isBefore(nightStart) ? nightStart : start;
            LocalDateTime overlapEnd = end.isAfter(nightEnd) ? nightEnd : end;
            night = Duration.between(overlapStart, overlapEnd).toHours();
        }

        // Output the results
        System.out.println("Morning segment (6:00 - 14:00): " + morning + " hours");
        System.out.println("Non-morning segment (14:00 - 22:00): " + nonMorning + " hours");
        System.out.println("Night segment (22:00 - 6:00): " + night + " hours");

        model.addAttribute("morning", morning);
        model.addAttribute("nonMorning", nonMorning);
        model.addAttribute("night", night);

        //employeeWorkHourRepository.save(employeeWorkHour);


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
