package com.hita.shifttracker.controller;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.dto.OrgUnitWorkingTimeReportDTO;
import com.hita.shifttracker.dto.WorkingTimeDTO;
import com.hita.shifttracker.dto.WorkingTimeReportDTO;
import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.WorkHourReportRepository;
import com.hita.shifttracker.service.*;
import com.hita.shifttracker.utils.TimeConverterHelper;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;



@Controller
public class HeadNurseController {

    private final AppUserService appUserService;
    private final CompanyService companyService;
    private final WorkingTimeItemService workingTimeItemService;
    private final WorkingTimeService workingTimeService;
    private final TempSchedulePerEmployeeService tempSchedulePerEmployeeService;
    private final WorkHourReportService workHourReportService;

    public HeadNurseController(AppUserService appUserService, CompanyService companyService,
                               WorkingTimeItemService workingTimeItemService, WorkingTimeService workingTimeService,
                               TempSchedulePerEmployeeService tempSchedulePerEmployeeService, WorkHourReportService workHourReportService) {
        this.appUserService = appUserService;
        this.companyService = companyService;
        this.workingTimeItemService = workingTimeItemService;
        this.workingTimeService = workingTimeService;
        this.tempSchedulePerEmployeeService = tempSchedulePerEmployeeService;
        this.workHourReportService = workHourReportService;
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

        //workingTimeService.deleteWorkingTimeById(employeeId, workingTimeToDelete);

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

    @GetMapping("/head_nurse/workhour/report")
    public String getWorkHourReport(Model model, HttpSession session) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        List<AppUserDTO> employees = appUserService.getAllEmployees();
        model.addAttribute("employees", employees);

        return "head_nurse_workhour_report.html";
    }

    @GetMapping("/head_nurse/show/workhour/report")
    public String showEmployeeWorkHoursReport(HttpSession session,
                                              Model model,
                                              @RequestParam("searchType") String searchType,
                                              @RequestParam(value = "orgUnit", required = false) Integer orgUnitId,
                                              @RequestParam(value = "employeeId", required = false) List<Integer> employeeIds,
                                              @RequestParam("monthYear") String monthYear) {

        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        List<AppUserDTO> employees = appUserService.getAllEmployees();
        model.addAttribute("employees", employees);

        String[] parts = monthYear.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        if ("orgUnit".equals(searchType)) {
            if (orgUnitId.equals(9)){
                List<OrgUnitWorkingTimeReportDTO> allOrgUnitReport = workHourReportService.getAllOrgUnitReport(month, year);

                if(allOrgUnitReport != null && !allOrgUnitReport.isEmpty()) {
                    model.addAttribute("allOrgUnitReport", allOrgUnitReport);
                } else {
                    model.addAttribute("errorMessage", 1);
                }

            } else {
                List<WorkingTimeReportDTO> reports = workHourReportService.getOrgUnitEmployeeReport(orgUnitId, month, year);
                List<OrgUnitWorkingTimeReportDTO> orgUnitReport = workHourReportService.getOrgUnitReport(orgUnitId, month, year);

                if (orgUnitReport != null && !orgUnitReport.isEmpty() && reports != null & !reports.isEmpty()) {
                    model.addAttribute("orgUnitReport", orgUnitReport);
                    model.addAttribute("reports", reports);
                } else {
                    model.addAttribute("errorMessage", 1);
                }
            }

        } else if ("employee".equals(searchType)) {
            List<WorkingTimeReportDTO> reports = workHourReportService.getEmployeeReport(employeeIds, month, year);
            if (reports != null && !reports.isEmpty()) {
                model.addAttribute("reports", reports);
            } else {
                model.addAttribute("errorMessage", 1);
            }
        }

        return "head_nurse_workhour_report.html";
    }

    @GetMapping ("/head_nurse/statistics")
    public String getStatistics(HttpSession session, Model model) {
        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
        model.addAttribute("appUser", appUser);

        return "head_nurse_statistics";
    }

    @GetMapping("/head_nurse/apache-superset-railway-production-1c0e.up.railway.app")
    @ResponseBody
    public ResponseEntity<String> getSupersetData() {
        return ResponseEntity.ok("apache-superset-railway-production-1c0e.up.railway.app");
    }


//    @GetMapping("/head_nurse/workhour/report/download")
//    public ResponseEntity<byte[]> getEmployeeWorkHourReport(@RequestParam ("employeeId") int employeeId,
//                                            @RequestParam ("monthYearSelect") String monthYearSelect,
//                                            HttpSession session,
//                                            Model model) throws IOException {
//
//        AppUserDTO employee = (AppUserDTO) appUserService.getEmployeeById(employeeId);
//
//        String[] parts = monthYearSelect.split("-");
//        int year = Integer.parseInt(parts[0]);
//        int month = Integer.parseInt(parts[1]);
//
//        List<WorkingTimeItemView> workingTimeItemsView = workingTimeItemService.getWorkingTimeItemViewByAppUser(employee, month, year);
//
//        byte[] excelBytes = generateExcelReport(employee, workingTimeItemsView, month, year);
//
//        String fileName = employee.getFirstName() + "_" + employee.getLastName() + "_izvjestaj_" + monthYearSelect + ".xlsx";
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(excelBytes);
//    }

//    private byte[] generateExcelReport(AppUserDTO employee, List<WorkingTimeItemView> workingTimeItems, int month, int year) throws IOException {
//        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//
//            Sheet sheet = workbook.createSheet("Work Hours");
//
//            // STILOVI
//            CellStyle boldBorderStyle = workbook.createCellStyle();
//            Font boldFont = workbook.createFont();
//            boldFont.setBold(true);
//            boldBorderStyle.setFont(boldFont);
//            boldBorderStyle.setBorderTop(BorderStyle.THIN);
//            boldBorderStyle.setBorderBottom(BorderStyle.THIN);
//            boldBorderStyle.setBorderLeft(BorderStyle.THIN);
//            boldBorderStyle.setBorderRight(BorderStyle.THIN);
//
//            CellStyle headerStyle = workbook.createCellStyle();
//            headerStyle.setFont(boldFont);
//            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            headerStyle.setBorderTop(BorderStyle.THIN);
//            headerStyle.setBorderBottom(BorderStyle.THIN);
//            headerStyle.setBorderLeft(BorderStyle.THIN);
//            headerStyle.setBorderRight(BorderStyle.THIN);
//
//            CellStyle borderStyle = workbook.createCellStyle();
//            borderStyle.setBorderTop(BorderStyle.THIN);
//            borderStyle.setBorderBottom(BorderStyle.THIN);
//            borderStyle.setBorderLeft(BorderStyle.THIN);
//            borderStyle.setBorderRight(BorderStyle.THIN);
//
//            // HEADER (Ime i Razdoblje)
//            Row row0 = sheet.createRow(0);
//            row0.createCell(0).setCellValue("Ime zaposlenika: " + employee.getFirstName() + " " + employee.getLastName());
//
//            Row row1 = sheet.createRow(1);
//            row1.createCell(0).setCellValue("Razdoblje: " + getMonthName(month) + " " + year);
//
//            // PRAZAN RED
//            sheet.createRow(2);
//
//            // TABLICA HEADER
//            Row headerRow = sheet.createRow(3);
//            headerRow.createCell(0).setCellValue("Vrsta rada");
//            headerRow.createCell(1).setCellValue("Ukupno");
//            headerRow.getCell(0).setCellStyle(headerStyle);
//            headerRow.getCell(1).setCellStyle(headerStyle);
//
//            // DODAVANJE REDOVA
//            int rowNum = 4;
//            double ukupnoSati = 0;
//
//            for (WorkingTimeItemView item : workingTimeItems) {
//                Row row = sheet.createRow(rowNum++);
//                String workType = item.getWorkTypeName() + " - " + item.getCopNum();
//                double totalHours = item.getTotal().doubleValue();
//
//                row.createCell(0).setCellValue(workType);
//                row.createCell(1).setCellValue(totalHours);
//                row.getCell(0).setCellStyle(borderStyle);
//                row.getCell(1).setCellStyle(borderStyle);
//                ukupnoSati += totalHours;
//            }
//
//            // RED ZA UKUPNE SATE
//            Row totalRow = sheet.createRow(rowNum);
//            totalRow.createCell(0).setCellValue("Ukupno sati");
//            totalRow.createCell(1).setCellValue(ukupnoSati);
//            totalRow.getCell(0).setCellStyle(headerStyle);
//            totalRow.getCell(1).setCellStyle(headerStyle);
//
//            // AUTOFIT KOLONE
//            sheet.autoSizeColumn(0);
//            sheet.autoSizeColumn(1);
//
//            // PRAZAN RED
//            sheet.createRow(rowNum + 2);
//            sheet.createRow(rowNum + 3);
//
//            // RED S CRTAMA ZA POTPIS
//            Row signatureRow1 = sheet.createRow(rowNum + 4);
//            signatureRow1.createCell(0).setCellValue("______________________");
//
//            Row signatureRow2 = sheet.createRow(rowNum + 7);
//            signatureRow2.createCell(0).setCellValue("______________________");
//
//            Row signatureRow3 = sheet.createRow(rowNum + 10);
//            signatureRow3.createCell(0).setCellValue("______________________");
//
//            // RED S OPISOM ISPOD CRTA
//            Row descriptionRow1 = sheet.createRow(rowNum + 5);
//            descriptionRow1.createCell(0).setCellValue("(Datum zaključenja evidencije)");
//
//            Row descriptionRow2 = sheet.createRow(rowNum + 8);
//            descriptionRow2.createCell(0).setCellValue("(Evidenciju popunio djelatnik)");
//
//            Row descriptionRow3 = sheet.createRow(rowNum + 11);
//            descriptionRow3.createCell(0).setCellValue("(Odobrio)");
//
//            // PRAZAN RED ispod potpisa
//            sheet.createRow(rowNum + 14);
//
//            // PRAVNA NAPOMENA
//            Row legalNoteRow = sheet.createRow(rowNum + 15);
//            legalNoteRow.createCell(0).setCellValue("Napomena: ");
//            Row legalTextRow = sheet.createRow(rowNum + 16);
//            legalTextRow.createCell(0).setCellValue("Obveza iz čl. 13 Pravilnika o sadržaju i načinu vođenja evidencije o radnicima zaposlenim kod poslodavca");
//            Row legalTextRow2 = sheet.createRow(rowNum + 17);
//            legalTextRow2.createCell(0).setCellValue("(Nar. novine broj 55/24).");
//
//
//            workbook.write(outputStream);
//            return outputStream.toByteArray();
//        }
//    }
//
//    private String getMonthName(int month) {
//        return switch (month) {
//            case 1 -> "Siječanj";
//            case 2 -> "Veljača";
//            case 3 -> "Ožujak";
//            case 4 -> "Travanj";
//            case 5 -> "Svibanj";
//            case 6 -> "Lipanj";
//            case 7 -> "Srpanj";
//            case 8 -> "Kolovoz";
//            case 9 -> "Rujan";
//            case 10 -> "Listopad";
//            case 11 -> "Studeni";
//            case 12 -> "Prosinac";
//            default -> "Nepoznato";
//        };
//    }

    // overtime
//    @GetMapping("/employee/workhour/overtime")
//    public String employeeWorkHourOvertimeProcess(@RequestParam("overtimeStart") String overtimeStart,
//                                                  @RequestParam("overtimeEnd") String overtimeEnd,
//                                                  Model model, HttpSession session /*request parameter employee id*/) {
//
//        AppUserDTO appUser = (AppUserDTO) session.getAttribute("appUser");
//
//        LocalDateTime overtimeStartDT = LocalDateTime.parse(overtimeStart);
//        LocalDateTime overtimeEndDT = LocalDateTime.parse(overtimeEnd);
//
//        System.out.println("overtime start: " + overtimeEndDT);
//        System.out.println("overtime end: " + overtimeEndDT);
//
//        LocalDate overtimeDateFrom = overtimeStartDT.toLocalDate();
//        LocalDate overtimeDateTo = overtimeEndDT.toLocalDate();
//
//        BigDecimal overtimeHoursFrom = TimeConverterHelper.convertAndRoundToHalf(
//                overtimeStartDT.toLocalTime().getHour(), overtimeStartDT.toLocalTime().getMinute());
//        BigDecimal overtimeHoursTo = TimeConverterHelper.convertAndRoundToHalf(
//                overtimeEndDT.toLocalTime().getHour(), overtimeEndDT.toLocalTime().getMinute());
//
//        // create overtime object
//        WorkingOvertime workingOvertime = new WorkingOvertime();
//        workingOvertime.setDateFrom(overtimeDateFrom);
//        workingOvertime.setHoursFrom(overtimeHoursFrom);
//        workingOvertime.setDateTo(overtimeDateTo);
//        workingOvertime.setHoursTo(overtimeHoursTo);
//        //workingOvertime.setAppUserId(); // TODO employee Id
//
//        workingTimeService.addOvertimeWorkHour(workingOvertime);
//
//        return "redirect:/employee/workhour/list";
//    }

}
