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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class HeadNurseController {

    private final AppUserService appUserService;
    private final CompanyService companyService;
    private final WorkingTimeItemService workingTimeItemService;
    private final WorkingTimeService workingTimeService;
    private final TempSchedulePerEmployeeService tempSchedulePerEmployeeService;

    public HeadNurseController(AppUserService appUserService, CompanyService companyService,
                               WorkingTimeItemService workingTimeItemService, WorkingTimeService workingTimeService,
                               TempSchedulePerEmployeeService tempSchedulePerEmployeeService) {
        this.appUserService = appUserService;
        this.companyService = companyService;
        this.workingTimeItemService = workingTimeItemService;
        this.workingTimeService = workingTimeService;
        this.tempSchedulePerEmployeeService = tempSchedulePerEmployeeService;
    }

    // Popis svih zaposlenika
    @GetMapping("/head_nurse/employee/list")
    public String getEmployeeList(Model model, HttpSession session) {
        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);
        // find all employees
        List<AppUserDTO> employees = appUserService.getAllEmployees();
        model.addAttribute("employees", employees);

        return "head_nurse_employee_list";
    }

    // Radni sati po zaposleniku
    @GetMapping("/head_nurse/workhour/list")
    public String getEmployeeWorkhour(Model model, HttpSession session, @RequestParam("id") int id) {
        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        AppUserDTO employee = appUserService.getEmployeeById(id);
        model.addAttribute("employee", employee);

        Company company = companyService.findWithData();
        model.addAttribute("company", company);

        return "head_nurse_employee_workhour";
    }

    @GetMapping("/head_nurse/workhour/report")
    public String getWorkHourReport(Model model, HttpSession session) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        return "head_nurse_workhour_report.html";
    }

    @GetMapping("/head_nurse/employee/workhour/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getEmployeeWorkHours(HttpSession session,
                                                                    @RequestParam int month,
                                                                    @RequestParam int year,
                                                                    @RequestParam int id) {

        System.out.println(month + year + id);

        AppUserDTO employee = appUserService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<WorkingTimeItemView> workingTimeItemsView = workingTimeItemService.getWorkingTimeItemViewByAppUser(employee, month, year);
        Map<LocalDate, List<WorkingTimeDTO>> workingTimeMap = workingTimeService.getWorkingHoursForMonth(employee.getId(), year, month);
        Period period = workingTimeService.getByMonthAndYear(month, year);
        // return schedule data;
        List<TempSchedulePerEmployeeView> tempSchedulePerEmployees = tempSchedulePerEmployeeService.findAllByAppUserIdAndMonthAndYearPivot(employee.getId(), month, year);

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

    @GetMapping("/head_nurse/employee/workhour/process")
    public String employeeWorkHourProcess(@RequestParam("startShift") String startShift, @RequestParam("endShift") String endShift,
                                          @RequestParam("employeeId") int employeeId,
                                          @RequestParam(value = "workingTimeId", required = false) Integer workingTimeId,
                                          Model model, HttpSession session){

        System.out.println(workingTimeId);

        AppUserDTO employee = appUserService.getEmployeeById(employeeId);

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
        workingTime.setAppUserId(employee.getId());
        workingTime.setShiftId(shiftType);
        workingTime.setSchedId(1);

        workingTimeService.addWorkingTime(workingTime);

        return "redirect:/head_nurse/workhour/list?id=" + employee.getId();
    }

    @GetMapping("/head_nurse/employee/workhour/delete")
    public String employeeWorkHourDelete(@RequestParam("workingTimeToDelete") int workingTimeToDelete, @RequestParam("employeeId") int employeeId, HttpSession session){

        workingTimeService.deleteWorkingTimeById(employeeId, workingTimeToDelete);

        return "redirect:/head_nurse/workhour/list?id=" + employeeId;
    }

    // Dodaj novog zaposlenika
    @GetMapping("/head_nurse/add/employee")
    public String employeeNewProcess(Model model,
                                     @RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam("oib") String oib,
                                     @RequestParam("telephone") String telephone,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("teamRole") int teamRole,
                                     @RequestParam("orgUnit") int orgUnit,
                                     @RequestParam("team") int team){

        AppUser employee = new AppUser();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setOib(oib);
        employee.setTelephone(telephone);
        employee.setEmail(email);
        employee.setPassword(password);
        employee.setTeamRoleId(teamRole);
        employee.setWorkRoleId(teamRole);
        employee.setOrganizationUnitId(orgUnit);
        employee.setTeamId(team);

        appUserService.saveEmployee(employee);
        return "redirect:/head_nurse/employee/list";
    }

}
