package com.hita.shifttracker.controller;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.EmployeeWorkHour;
import com.hita.shifttracker.repository.AppUserRepository;
import com.hita.shifttracker.repository.EmployeeWorkHourRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HeadNurseController {

    private AppUserRepository appUserRepository;

    public HeadNurseController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

//    @Autowired
//    AppUserRepository appUserRepositoryDB;
//
//    @Autowired
//    EmployeeWorkHourRepository employeeWorkHourRepositoryDB;

    @GetMapping("/head-nurse/dashboard")
    public String getHeadNurseDashboard(Model model, HttpSession session) {
        AppUser appUser = (AppUser) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        List<AppUser> employees = appUserRepository.findByAppRoleId(1);
        model.addAttribute("employees", employees);

        return "head-nurse/head-nurse-dashboard.html";
    }

    // vidi satnicu pojedinog zaposlenika
    @GetMapping("/head-nurse/employeeWorkHours")
    public String employeeWorkHours(@RequestParam("id") int id, Model model, HttpSession session){
        System.out.println("employee id: " + id);

        AppUser appUser = (AppUser) session.getAttribute("appUser");

        AppUser employee = appUserRepository.findById(id).get();
        System.out.println("ime: " + employee.getFirstName() + ", prezime:" + employee.getLastName());

        model.addAttribute("appUser", appUser);
        model.addAttribute("employee", employee);

        return "/head-nurse/head-nurse-employeeHours.html";
    }
//
//    @GetMapping("/employeeList")
//    public String employeeList(Model model) {
//
//        model.addAttribute("employees", appUserRepositoryDB.findByAppRoleId(1));
//        return "head-nurse/employeeList.html";
//    }
//
//    @GetMapping("/employeeWorkHours")
//    public String employeeHours(@RequestParam int id,
//                                Model model){
//
//        AppUser employee = appUserRepositoryDB.findById(id).get();
//        List<EmployeeWorkHour> employeeWorkHours = employeeWorkHourRepositoryDB.findByAppUserId(id);
//        model.addAttribute("employeeWorkHours", employeeWorkHours);
//        model.addAttribute("employee", employee);
//
//        return "head-nurse/employeeWorkHours";
//    }
//
//    @GetMapping("/deleteEmployeeWorkHour")
//    public String deleteEmployeeWorkHour(@RequestParam int workHourId,
//                                  @RequestParam int employeeId,
//                                  Model model){
//
//        employeeWorkHourRepositoryDB.deleteById(workHourId);
//
//        return "redirect:/employeeWorkHours?id=" + employeeId;
//    }
//
//    @GetMapping("/showEmployeeWorkHour")
//    public String showEmployeeWorkHour(@RequestParam int workHourId,
//                                       Model model) {
//
//        EmployeeWorkHour employeeWorkHourToEdit = employeeWorkHourRepositoryDB.findById(workHourId).get();
//        model.addAttribute("employeeWorkHour", employeeWorkHourToEdit);
//
//        return "head-nurse/editEmployeeWorkHour";
//    }
//
//    @GetMapping("/editEmployeeWorkHour")
//    public String editEmployeeHour(@RequestParam int workHourId,
//                                    @RequestParam int employeeId,
//                                    @RequestParam String startTime,
//                                    @RequestParam String endTime,
//                                    Model model) {
//        LocalDateTime start = LocalDateTime.parse(startTime);
//        LocalDateTime end = LocalDateTime.parse(endTime);
//        int totalHours = (int) Duration.between(start, end).toHours();
//
//        EmployeeWorkHour employeeWorkHourToEdit = employeeWorkHourRepositoryDB.findById(workHourId).get();
//        employeeWorkHourToEdit.setStartTime(start);
//        employeeWorkHourToEdit.setEndTime(end);
//        employeeWorkHourToEdit.setTotalHours(totalHours);
//
//
//        employeeWorkHourRepositoryDB.save(employeeWorkHourToEdit);
//
//        return "redirect:/employeeWorkHours?id=" + employeeId;
//    }
//
//    @GetMapping("/adminNewEmployeeWorkHour")
//    public String adminAddEmployeeWorkHour(@RequestParam int employeeId,
//                                           Model model) {
//
//        AppUser employee = appUserRepositoryDB.findById(employeeId).get();
//        model.addAttribute("employee", employee);
//
//        return "head-nurse/adminAddEmployeeWorkHour";
//    }
//
//    @GetMapping("/adminAddEmployeeWorkHour")
//    public String adminAddNewEmployeeShift(@RequestParam int employeeId,
//                                           @RequestParam("startTime") String startTime,
//                                           @RequestParam("endTime") String endTime,
//                                           Model model) {
//
//        AppUser employee = appUserRepositoryDB.findById(employeeId).get();
//
//        LocalDateTime start = LocalDateTime.parse(startTime);
//        LocalDateTime end = LocalDateTime.parse(endTime);
//        int totalHours = (int) Duration.between(start, end).toHours();
//
//        EmployeeWorkHour newEmployeeWorkHour = new EmployeeWorkHour(start, end, totalHours);
//        newEmployeeWorkHour.setAppUser(employee);
//        employeeWorkHourRepositoryDB.save(newEmployeeWorkHour);
//
//        return "redirect:/employeeWorkHours?id=" + employeeId;
//
//    }
}
