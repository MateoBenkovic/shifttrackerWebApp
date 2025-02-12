package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HeadNurseController {

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Autowired
    private OrganizationUnitRepository organizationUnitRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamRoleRepository teamRoleRepository;

    private AppUserRepository appUserRepository;

    public HeadNurseController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("head_nurse/dashboard")
    public String getHeadNurseDashboard(HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        return "redirect:/head_nurse/employee/list";
    }

    @GetMapping("/head_nurse/employee/list")
    public String getEmployeeList(Model model, HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);


        List<AppUser> employees = appUserRepository.findAllByAppRoleId(1);
        model.addAttribute("employees", employees);

        for (AppUser employee : employees) {
            System.out.println(employee.toString());
        }

        return "head_nurse_employee_list";
    }

    @GetMapping("/head_nurse/workhour/list")
    public String getEmployeeWorkhour(Model model, HttpSession session, @RequestParam("id") int id) {
        AppUser employee = (AppUser) appUserRepository.findById(id).get();

        model.addAttribute("employee", employee);

        return "head_nurse_employee_workhour";
    }

    @GetMapping("/head_nurse/add/user")
    public String addNewUser(Model model, HttpSession session,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("oib") String oib,
                             @RequestParam("telephone") String telephone,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("teamRole") int teamRole,
                             @RequestParam("orgUnit") int orgUnit,
                             @RequestParam("team") int team) {

        AppRole appRole = appRoleRepository.findById(1).get();
        OrganizationUnit employeeOrgUnit = organizationUnitRepository.findById(orgUnit).get();
        TeamRole employeeTeamRole = teamRoleRepository.findById(teamRole).get();
        Team employeeTeam = teamRepository.findById(team).get();

        AppUser newEmployee = new AppUser(firstName, lastName, oib, telephone, employeeOrgUnit, appRole, employeeTeamRole, employeeTeam, email, password);
        appUserRepository.save(newEmployee);

        return "redirect:/head_nurse/employee/list";
    }
}
