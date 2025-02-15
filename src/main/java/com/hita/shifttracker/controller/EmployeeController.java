package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.Company;
import com.hita.shifttracker.model.WorkingTime;
import com.hita.shifttracker.service.CompanyService;
import com.hita.shifttracker.service.WorkingTimeItemService;
import com.hita.shifttracker.service.WorkingTimeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Controller
public class EmployeeController {


    private final CompanyService companyService;
    private final WorkingTimeService workingTimeService;
    private final WorkingTimeItemService workingTimeItemService;

    private EmployeeController(CompanyService companyService, WorkingTimeService workingTimeService,
                               WorkingTimeItemService workingTimeItemService) {
        this.companyService = companyService;
        this.workingTimeService = workingTimeService;
        this.workingTimeItemService = workingTimeItemService;
    }

    @GetMapping("/employee/workhour/list")
    public String getEmployeeWorkHourList(Model model, HttpSession session) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        Company company = companyService.findWithData();

        model.addAttribute("appUser", appUser);
        model.addAttribute("company", company);

        // calendar service

        // get all workhour by employee
        List<Map<String, Object>> workingTimeItems = workingTimeItemService.getWorkingTimeItemByDays(appUser.getId(), 2,2025);
        model.addAttribute("wtis", workingTimeItems);

        return "employee_workhour_list";
    }

    @GetMapping("/employee/workhour/process")
    public String employeeWorkHourProcess(@RequestParam("startShift") String startShift, @RequestParam("endShift") String endShift,
                                          Model model, HttpSession session){

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");

        LocalDateTime startDateTime = LocalDateTime.parse(startShift);
        LocalDateTime endDateTime = LocalDateTime.parse(endShift);

        LocalDate dateFrom = startDateTime.toLocalDate();
        int hoursFrom = startDateTime.toLocalTime().getHour();
        LocalDate dateTo = endDateTime.toLocalDate();
        int hoursTo = endDateTime.toLocalTime().getHour();

        int shiftType = 2;
        if(dateFrom.equals(dateTo)) {
            shiftType = 1;
        }

        WorkingTime workingTime = new WorkingTime();
        workingTime.setDateFrom(dateFrom);
        workingTime.setHoursFrom(hoursFrom);
        workingTime.setDateTo(dateTo);
        workingTime.setHoursTo(hoursTo);
        workingTime.setAppUserId(appUser.getId());
        workingTime.setShiftId(shiftType);
        workingTime.setSchedId(1);

        workingTimeService.addWorkingTime(workingTime);

        return "redirect:/employee/workhour/list";
    }

}
