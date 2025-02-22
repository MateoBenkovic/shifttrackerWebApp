package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.dto.WorkingTimeDTO;
import com.hita.shifttracker.model.*;
import com.hita.shifttracker.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@Controller
public class EmployeeController {


    private final CompanyService companyService;
    private final WorkingTimeService workingTimeService;
    private final WorkingTimeItemService workingTimeItemService;
    private final DateService dateService;
    private final TempSchedulePerEmployeeService tempSchedulePerEmployeeService;

    private EmployeeController(CompanyService companyService, WorkingTimeService workingTimeService,
                               WorkingTimeItemService workingTimeItemService, DateService dateService,
                               TempSchedulePerEmployeeService tempSchedulePerEmployeeService) {
        this.companyService = companyService;
        this.workingTimeService = workingTimeService;
        this.workingTimeItemService = workingTimeItemService;
        this.dateService = dateService;
        this.tempSchedulePerEmployeeService = tempSchedulePerEmployeeService;
    }

    @GetMapping("/employee/workhour/list")
    public String getEmployeeWorkHourList(Model model, HttpSession session) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        Company company = companyService.findWithData();
        int month = dateService.getCurrentMonthFromDatabase();
        int year = dateService.getCurrentYearFromDatabase();

        model.addAttribute("appUser", appUser);
        model.addAttribute("company", company);


        return "employee_workhour_list";
    }

    @GetMapping("/employee/workhour/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getEmployeeWorkHours(HttpSession session,
                                                                    @RequestParam int month,
                                                                    @RequestParam int year) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");

        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<WorkingTimeItemView> workingTimeItemsView = workingTimeItemService.getWorkingTimeItemViewByAppUser(appUser, month, year);
        Map<LocalDate, List<WorkingTimeDTO>> workingTimeMap = workingTimeService.getWorkingHoursForMonth(appUser.getId(), year, month);
        Period period = workingTimeService.getByMonthAndYear(month, year);
        // return schedule data;
        List<TempSchedulePerEmployeeView> tempSchedulePerEmployees = tempSchedulePerEmployeeService.findAllByAppUserIdAndMonthAndYearPivot(appUser.getId(), month, year);

        for (TempSchedulePerEmployeeView tempSchedulePerEmployee : tempSchedulePerEmployees) {
            System.out.println(tempSchedulePerEmployee.toString());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("workHours", workingTimeItemsView);
        response.put("workingTimes", workingTimeMap);
        response.put("period", period);
        response.put("schedule", tempSchedulePerEmployees);

        return ResponseEntity.ok(response);
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

    @GetMapping("/employee/workhour/add")
    public String getEmployeeWorkHourAdd(Model model, HttpSession session){
        AppUserDTO appUserDTO = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUserDTO);

        return "employee_workhour_add";
    }

    @GetMapping("/employee/workhour/remove")
    public String employeeWorkHourRemove(@RequestParam("startShift") String startShift,
                                         @RequestParam("endShift") String endShift, HttpSession session){

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        System.out.println("in delete");
        LocalDateTime startDateTime = LocalDateTime.parse(startShift);
        LocalDate dateFrom = startDateTime.toLocalDate();
        workingTimeService.deleteWorkingTimeByAppUserIdAndDateFrom(appUser.getId(), dateFrom);

        return "redirect:/employee/workhour/list";
    }


    @GetMapping("/employee/workhour/overtime")
    public String employeeWorkHourOvertimeProcess() {

        // ispisati datume i sate koje je korisnik unio u prekovremene

        return "redirect:/employee/workhour/list";
    }


}
