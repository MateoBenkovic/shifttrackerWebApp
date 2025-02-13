package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.dto.CompanyDTO;
import com.hita.shifttracker.model.WorkingTime;
import com.hita.shifttracker.model.WorkingTimeUserWtCalView;
import com.hita.shifttracker.repository.WorkingTimeUserWtCalViewRepository;
import com.hita.shifttracker.service.CompanyService;
import com.hita.shifttracker.service.WorkingTimeService;
import jakarta.servlet.http.HttpSession;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class EmployeeController {


    private final CompanyService companyService;
    private final WorkingTimeService workingTimeService;
    private final WorkingTimeUserWtCalViewRepository wtuvRepository;

    private EmployeeController(CompanyService companyService, WorkingTimeService workingTimeService,
                               WorkingTimeUserWtCalViewRepository wtuvRepository) {
        this.companyService = companyService;
        this.workingTimeService = workingTimeService;
        this.wtuvRepository = wtuvRepository;
    }

    @GetMapping("/employee/workhour/list")
    public String getEmployeeWorkHourList(Model model, HttpSession session) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        CompanyDTO company = companyService.findByIdWithData(1);
        List<WorkingTimeUserWtCalView> wtuvs = wtuvRepository.findAllByAppUserCode(appUser.getUserCode(), 2);

        model.addAttribute("appUser", appUser);
        model.addAttribute("company", company);
        model.addAttribute("wtuvs", wtuvs);

        // get all workhour by employee

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


//
//    @Autowired
//    public EmployeeController(WorkingTimeRepository workingTimeRepository, AppUserRepository appUserRepository,
//                              /*WorkingTimeUserWtCalViewRepository workingTimeUserWtCalViewRepository, */CompanyRepository companyRepository,
//                              WorkingTimeItemRepository workingTimeItemRepository) {
//        this.companyRepository = companyRepository;
//        this.workingTimeRepository = workingTimeRepository;
//        this.appUserRepository = appUserRepository;
//       // this.workingTimeUserWtCalViewRepository = workingTimeUserWtCalViewRepository;
//        this.workingTimeItemRepository = workingTimeItemRepository;
//    }
//
//    // employee workhour list
//    @GetMapping("/employee/dashboard")
//    public String getDashboard(HttpSession session) {
//        AppUser appUser = (AppUser) session.getAttribute("appUser");
//        return "redirect:/employee/workhour/list";
//    }
//
//    @GetMapping("/employee/workhour/add")
//    public String getEmployeeWorkHourAdd(Model model, HttpSession session){
//        AppUser appUser = (AppUser) session.getAttribute("appUser");
//        model.addAttribute("appUser", appUser);
//
//        return "employee_workhour_add";
//    }
//
//    @GetMapping("/employee/workhour/process")
//    public String employeeWorkHourProcess(@RequestParam("startShift") String startShift, @RequestParam("endShift") String endShift, Model model, HttpSession session){
//        AppUser appUser = (AppUser) session.getAttribute("appUser");
//        model.addAttribute("appUser", appUser);
//
//        System.out.println("startShift: " + startShift);
//        System.out.println("endShift: " + endShift);
//
//        LocalDateTime startDateTime = LocalDateTime.parse(startShift);
//        LocalDateTime endDateTime = LocalDateTime.parse(endShift);
//
//        LocalDate dateFrom = startDateTime.toLocalDate();
//        int hoursFrom = startDateTime.toLocalTime().getHour();
//        LocalDate dateTo = endDateTime.toLocalDate();
//        int hoursTo = endDateTime.toLocalTime().getHour();
//
//        int shift = 2;
//        if(dateFrom.equals(dateTo)){
//            shift = 1;
//        }
//
//        int totalHours = Math.abs(hoursFrom - hoursTo);
//
//        System.out.println("dateFrom: " + dateFrom);
//        System.out.println("hoursFrom: " + hoursFrom);
//        System.out.println("dateTo: " + dateTo);
//        System.out.println("hoursTo: " + hoursTo);
//        System.out.println("totalHours: " + totalHours);
//        System.out.println("appUserId: " + appUser.getId());
//        System.out.println("appUser: " + appUser.getFirstName() + " " + appUser.getLastName());
//
//        WorkingTime workingTime = new WorkingTime();
//        workingTime.setDateFrom(dateFrom);
//        workingTime.setHoursFrom(hoursFrom);
//        workingTime.setDateTo(dateTo);
//        workingTime.setHoursTo(hoursTo);
//        workingTime.setTotalHours(totalHours);
//        workingTime.setAppUser(appUser);
//        workingTime.setShiftId(shift);
//
//        workingTimeRepository.saveWorkingTime(dateFrom, hoursFrom, dateTo, hoursTo, totalHours, appUser.getId(), shift);
//
//
//
//
//        return "redirect:/employee/workhour/list";
//    }
//
//    @GetMapping("/employee/workhour/list")
//    public String getEmployeeWorkHourList(Model model, HttpSession session){
//        AppUser appUser = (AppUser) session.getAttribute("appUser");
//        model.addAttribute("appUser", appUser);
//
//        Company company = companyRepository.findById(1).get();
//        model.addAttribute("company", company);
//
//        String appUserCode = appUserRepository.findAppUserCodeById(appUser.getId());
//        System.out.println("appUserCode: " + appUserCode);
//
//        // work time item
//        // get all work time ithem where mont equals 2 and app user id
//
//        // posalji mi vrste rada po useru
//
//
////        // wiew by app user code
////        List<WorkingTimeUserWtCalViewDTO> workingTimesUserView = workingTimeUserWtCalViewRepository.findAllRecords(appUserCode, 2, 2025);
////        for(WorkingTimeUserWtCalViewDTO wt : workingTimesUserView) {
////            System.out.println(wt.toString());
////        }
////        model.addAttribute("workingTimesUserView", workingTimesUserView);
//
//        return "employee_workhour_list";
//    }



}
