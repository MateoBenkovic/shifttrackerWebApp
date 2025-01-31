package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.SchedulePerMonth;
import com.hita.shifttracker.repository.EmployeeWorkHourRepository;
import com.hita.shifttracker.repository.SchedulePerMonthRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.*;
import java.util.List;

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
        List<SchedulePerMonth> workHours = schedulePerMonthRepository.findByAppUserId(appUser.getId());
        model.addAttribute("workHours", workHours);

        return "/employee/employee-schedule.html";
    }

    // ispuni smjenu // shift id
    @GetMapping("/employee/submit-shift")
    public String getSubmitShift(@RequestParam("id") int id, Model model, HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        System.out.println("id: " + id);

        return "employee/employee-add-work-hour.html";
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

        LocalDateTime startDateTime = LocalDateTime.parse(startTime);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime);

        String ispis = "";

        int hourStart = startDateTime.getHour();
        int hourEnd = 24;
        if (startDateTime.getDayOfYear() == endDateTime.getDayOfYear()) {
            hourEnd = endDateTime.getHour();
        }



        // pocetak

        LocalTime morningStart = LocalTime.of(6, 0);  // 6 AM
        LocalTime noonStart = LocalTime.of(14, 0);    // 2 PM
        LocalTime nightStart = LocalTime.of(22, 0);   // 10 PM
        LocalTime nightEnd = LocalTime.of(6, 0);

        LocalDateTime currentDateTime = startDateTime;

        while (!currentDateTime.toLocalDate().isAfter(endDateTime.toLocalDate())) {

            // Calculate the start and end of the current day period
            LocalDateTime startOfDay = currentDateTime.toLocalDate().atStartOfDay();
            LocalDateTime endOfDay = currentDateTime.toLocalDate().atTime(23, 59, 59, 999999999);

            // Make sure we only calculate hours for the specific day
            LocalDateTime startOfCurrentDay = currentDateTime.isBefore(startOfDay) ? startOfDay : currentDateTime;
            LocalDateTime endOfCurrentDay = endDateTime.isBefore(endOfDay) ? endDateTime : endOfDay;

            int morningHours = 0, noonHours = 0, nightHours = 0;

            // Loop through hours of the day from start to end
            LocalDateTime tempTime = startOfCurrentDay;
//            System.out.println("temp time = " + tempTime);
//            System.out.println("end of current day : " + endOfCurrentDay);
            while (tempTime.isBefore(endOfCurrentDay)) {

                LocalTime currentLocalTime = tempTime.toLocalTime();

                // Morning hours: 6 AM - 2 PM
                if (currentLocalTime.isAfter(morningStart.minusSeconds(1)) && currentLocalTime.isBefore(noonStart)) {
                    morningHours++;
                }
                // Noon hours: 2 PM - 10 PM
                else if (currentLocalTime.isAfter(noonStart.minusSeconds(1)) && currentLocalTime.isBefore(nightStart)) {
                    noonHours++;
                }
                // Night hours: 10 PM - 6 AM (next day)
                else {
                    nightHours++;
                }

                tempTime = tempTime.plusHours(1); // Move one hour forward
            }
            System.out.println("date: " + currentDateTime.toLocalDate());
            System.out.println("morning hours: " + morningHours);
            System.out.println("noon hours: " + noonHours);
            System.out.println("night hours: " + nightHours);
            System.out.println("hour start: " + hourStart);
            System.out.println("hour end: " + hourEnd);


             ispis += "Datum: " + currentDateTime.toLocalDate() + ", Redovan rad: " + morningHours + ", Redovan rad II smjena: " + noonHours + ", Redovan rad noc: " + nightHours;
            model.addAttribute("ispis", ispis);


            System.out.println(currentDateTime);
            currentDateTime = currentDateTime.plusDays(1).withHour(0);
            hourStart = 0;
            hourEnd = endDateTime.getHour();
        }
// kraj




        //employeeWorkHourRepository.save(employeeWorkHour);


        return "redirect:/employee/workHour/list";
    }

    @GetMapping("/goToMain")
    public String goToMain(){
        return "redirect:/employee/dashboard";
    }

    // get all workhours
    @GetMapping("/employee/workHour/list")
    public String getEmployeeWorkHours(Model model, HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);
        return "/employee/employee-list-work-hour.html";
    }
}
