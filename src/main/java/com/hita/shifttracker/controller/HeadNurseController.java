package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.dto.CompanyDTO;
import com.hita.shifttracker.model.WorkingTimeUserWtCalView;
import com.hita.shifttracker.repository.AppUserRepository;
import com.hita.shifttracker.repository.CompanyRepository;
import com.hita.shifttracker.repository.WorkingTimeUserWtCalViewRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HeadNurseController {

    private final AppUserRepository appUserRepository;
    private final CompanyRepository companyRepository;
    private final WorkingTimeUserWtCalViewRepository wtuvRepository;

    public HeadNurseController(AppUserRepository appUserRepository, CompanyRepository companyRepository,
                               WorkingTimeUserWtCalViewRepository wtuvRepository) {
        this.appUserRepository = appUserRepository;
        this.companyRepository = companyRepository;
        this.wtuvRepository = wtuvRepository;
    }

    // Popis svih zaposlenika
    @GetMapping("/head_nurse/employee/list")
    public String getEmployeeList(Model model, HttpSession session) {
        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        List<AppUserDTO> employees = appUserRepository.findAllEmployees();
        model.addAttribute("employees", employees);


        return "head_nurse_employee_list";
    }

    // Radni sati po zaposleniku
    @GetMapping("/head_nurse/workhour/list")
    public String getEmployeeWorkhour(Model model, HttpSession session, @RequestParam("id") int id) {
        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        // find employee
        AppUserDTO employee = appUserRepository.findEmployeeById(id);
        model.addAttribute("employee", employee);

        CompanyDTO company = companyRepository.findByIdWithData(1);
        model.addAttribute("company", company);

        List<WorkingTimeUserWtCalView> wtuvs = wtuvRepository.findAllByAppUserCode(employee.getUserCode(), 2);
        model.addAttribute("wtuvs", wtuvs);

        // data for new employee

        return "head_nurse_employee_workhour";
    }

    // Dodaj novog zaposlenika
    @GetMapping("/head_nurse/employee/new/process")
    public String employeeNewProcess(){
        return "redirect:/head_nurse/employee/list";
    }
}
