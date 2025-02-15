package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.Company;
import com.hita.shifttracker.service.AppUserService;
import com.hita.shifttracker.service.CompanyService;
import com.hita.shifttracker.service.WorkingTimeItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
public class HeadNurseController {

    private final AppUserService appUserService;
    private final CompanyService companyService;
    private final WorkingTimeItemService workingTimeItemService;

    public HeadNurseController(AppUserService appUserService, CompanyService companyService,
                               WorkingTimeItemService workingTimeItemService) {
        this.appUserService = appUserService;
        this.companyService = companyService;
        this.workingTimeItemService = workingTimeItemService;
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

        // working time item per employee by month and year
        // get all workhour by employee
        List<Map<String, Object>> workingTimeItems = workingTimeItemService.getWorkingTimeItemByDays(employee.getId(), 2,2025);
        model.addAttribute("wtis", workingTimeItems);

        return "head_nurse_employee_workhour";
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
