package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.Company;
import com.hita.shifttracker.model.WorkingTime;
//import com.hita.shifttracker.model.WorkingTimeUserWtCalView;
//import com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO;
import com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO;
import com.hita.shifttracker.repository.AppUserRepository;
import com.hita.shifttracker.repository.CompanyRepository;
import com.hita.shifttracker.repository.WorkingTimeRepository;
//import com.hita.shifttracker.repository.WorkingTimeUserWtCalViewRepository;
import com.hita.shifttracker.repository.WorkingTimeUserWtCalViewRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class EmployeeController {

    private WorkingTimeRepository workingTimeRepository;
    private AppUserRepository appUserRepository;
    private WorkingTimeUserWtCalViewRepository workingTimeUserWtCalViewRepository;
    // podatci od hitne
    private CompanyRepository companyRepository;


    public EmployeeController(WorkingTimeRepository workingTimeRepository, AppUserRepository appUserRepository,
                              WorkingTimeUserWtCalViewRepository workingTimeUserWtCalViewRepository, CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        this.workingTimeRepository = workingTimeRepository;
        this.appUserRepository = appUserRepository;
        this.workingTimeUserWtCalViewRepository = workingTimeUserWtCalViewRepository;

    }

    // employee workhour list
    @GetMapping("/employee/dashboard")
    public String getDashboard(HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        return "redirect:/employee/workhour/list";
    }
//
//    @GetMapping("/employee/workhour/add")
//    public String getEmployeeWorkHourAdd(Model model, HttpSession session){
//        AppUser appUser = (AppUser) session.getAttribute("appUser");
//        model.addAttribute("appUser", appUser);
//
//        return "employee_workhour_add";
//    }

    @GetMapping("/employee/workhour/process")
    public String employeeWorkHourProcess(@RequestParam("startShift") String startShift, @RequestParam("endShift") String endShift, Model model, HttpSession session){
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        System.out.println("startShift: " + startShift);
        System.out.println("endShift: " + endShift);

        LocalDateTime startDateTime = LocalDateTime.parse(startShift);
        LocalDateTime endDateTime = LocalDateTime.parse(endShift);

        LocalDate dateFrom = startDateTime.toLocalDate();
        int hoursFrom = startDateTime.toLocalTime().getHour();
        LocalDate dateTo = endDateTime.toLocalDate();
        int hoursTo = endDateTime.toLocalTime().getHour();

        int totalHours = Math.abs(hoursFrom - hoursTo);

        System.out.println("dateFrom: " + dateFrom);
        System.out.println("hoursFrom: " + hoursFrom);
        System.out.println("dateTo: " + dateTo);
        System.out.println("hoursTo: " + hoursTo);
        System.out.println("totalHours: " + totalHours);
        System.out.println("appUserId: " + appUser.getId());
        System.out.println("appUser: " + appUser.getFirstName() + " " + appUser.getLastName());

        WorkingTime workingTime = new WorkingTime();
        workingTime.setDateFrom(dateFrom);
        workingTime.setHoursFrom(hoursFrom);
        workingTime.setDateTo(dateTo);
        workingTime.setHoursTo(hoursTo);
        workingTime.setTotalHours(totalHours);
        workingTime.setAppUser(appUser);

        workingTimeRepository.save(workingTime);

        return "redirect:/employee/workhour/list";
    }

    @GetMapping("/employee/workhour/list")
    public String getEmployeeWorkHourList(Model model, HttpSession session){
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        Company company = companyRepository.findById(1).get();
        model.addAttribute("company", company);

        String appUserCode = appUserRepository.findAppUserCodeById(appUser.getId());
        System.out.println("appUserCode: " + appUserCode);

        // posalji mi vrste rada po useru


        // wiew by app user code
        List<WorkingTimeUserWtCalViewDTO> workingTimesUserView = workingTimeUserWtCalViewRepository.findAllRecords(appUserCode);
        for(WorkingTimeUserWtCalViewDTO wt : workingTimesUserView) {
            System.out.println(wt.toString());
        }
        model.addAttribute("workingTimesUserView", workingTimesUserView);

        return "employee_workhour_list";
    }



}
