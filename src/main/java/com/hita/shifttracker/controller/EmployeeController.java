package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.dto.WorkingTimeDTO;
import com.hita.shifttracker.model.*;
import com.hita.shifttracker.service.*;
import com.hita.shifttracker.utils.TimeConverterHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        //int month = dateService.getCurrentMonthFromDatabase();
        //int year = dateService.getCurrentYearFromDatabase();
        List<WorkTypesOther> workTypesOtherList = workingTimeService.findAllWorkTypesOther();

        model.addAttribute("appUser", appUser);
        model.addAttribute("company", company);
        model.addAttribute("workTypesOtherList", workTypesOtherList);
        // leave record
        model.addAttribute("leaveRecord", new LeaveRecord());
       // model.addAttribute("workTypesOther", new WorkTypesOther());


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
                                          @RequestParam(value = "workingTimeId", required = false) Integer workingTimeId,
                                          Model model, HttpSession session){

        System.out.println("id: " + workingTimeId);

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");

        LocalDateTime startDateTime = LocalDateTime.parse(startShift);
        LocalDateTime endDateTime = LocalDateTime.parse(endShift);

        LocalDate dateFrom = startDateTime.toLocalDate();
        int hoursFrom = startDateTime.toLocalTime().getHour();
        LocalDate dateTo = endDateTime.toLocalDate();
        int hoursTo = endDateTime.toLocalTime().getHour();


        WorkingTime workingTime = new WorkingTime();
        workingTime.setDateFrom(dateFrom);
        workingTime.setHoursFrom(hoursFrom);
        workingTime.setDateTo(dateTo);
        workingTime.setHoursTo(hoursTo);
        workingTime.setAppUserId(appUser.getId());
        workingTime.setUidInsUpd(appUser.getId());
        workingTime.setSchedId(1);

        if (workingTimeId == null) {
            workingTimeService.addWorkingTime(workingTime);
        } else if (workingTimeId != null) {
            // update working time
            workingTime.setIdWorkTime(workingTimeId);
            workingTimeService.updateWorkingTime(workingTime);
        }



        return "redirect:/employee/workhour/list";
    }

    @GetMapping("/employee/workhour/delete")
    public String employeeWorkHourDelete(@RequestParam("workingTimeToDelete") int workingTimeToDelete, HttpSession session){

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");

        workingTimeService.deleteWorkingTimeById(workingTimeToDelete, appUser.getId());

        return "redirect:/employee/workhour/list";
    }

    // leave records (odsutnosti)
    @PostMapping("/employee/workhour/leaveRecord")
    public String leaveRecordsProcess(@ModelAttribute LeaveRecord leaveRecord, HttpSession session) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        System.out.println("leave record: " + leaveRecord.toString());
        leaveRecord.setAppUserId(appUser.getId());
        leaveRecord.setUidInsUpd(appUser.getId());
        leaveRecord.setHoursPerDay(8);

        workingTimeService.addLeaveRecord(leaveRecord);
        return "redirect:/employee/workhour/list";
    }


    @GetMapping("/employee/workhour/overtime")
    public String employeeWorkHourOvertimeProcess(@RequestParam("overtimeStart") String overtimeStart,
                                                  @RequestParam("overtimeEnd") String overtimeEnd,
                                                  @RequestParam(value = "workingOvertimeId",required = false) Integer workingOvertimeId,
                                                  Model model, HttpSession session) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");

        LocalDateTime overtimeStartDT = LocalDateTime.parse(overtimeStart);
        LocalDateTime overtimeEndDT = LocalDateTime.parse(overtimeEnd);

        System.out.println("overtime start: " + overtimeEndDT);
        System.out.println("overtime end: " + overtimeEndDT);
        System.out.println("working overtime id: " + workingOvertimeId);

        LocalDate overtimeDateFrom = overtimeStartDT.toLocalDate();
        LocalDate overtimeDateTo = overtimeEndDT.toLocalDate();

        BigDecimal overtimeHoursFrom = TimeConverterHelper.convertAndRoundToHalf(
                overtimeStartDT.toLocalTime().getHour(), overtimeStartDT.toLocalTime().getMinute());
        BigDecimal overtimeHoursTo = TimeConverterHelper.convertAndRoundToHalf(
                overtimeEndDT.toLocalTime().getHour(), overtimeEndDT.toLocalTime().getMinute());

        // create overtime object
        WorkingOvertime workingOvertime = new WorkingOvertime();
        workingOvertime.setDateFrom(overtimeDateFrom);
        workingOvertime.setHoursFrom(overtimeHoursFrom);
        workingOvertime.setDateTo(overtimeDateTo);
        workingOvertime.setHoursTo(overtimeHoursTo);
        workingOvertime.setAppUserId(appUser.getId());

        workingTimeService.addOvertimeWorkHour(workingOvertime);

        return "redirect:/employee/workhour/list";
    }


}
