package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.WorkingTimeDTO;
import com.hita.shifttracker.model.*;
import com.hita.shifttracker.repository.PeriodRepository;
import com.hita.shifttracker.repository.WorkingOvertimeRepository;
import com.hita.shifttracker.repository.WorkingTimeItemRepository;
import com.hita.shifttracker.repository.WorkingTimeRepository;
import com.hita.shifttracker.utils.TimeConverterHelper;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkingTimeService {

    private final WorkingTimeRepository workingTimeRepository;
    private final PeriodRepository periodRepository;
    private final WorkingTimeItemRepository workingTimeItemRepository;
    private final WorkingOvertimeRepository workingOvertimeRepository;

    public WorkingTimeService(WorkingTimeRepository workingTimeRepository, PeriodRepository periodRepository,
                              WorkingTimeItemRepository workingTimeItemRepository, WorkingOvertimeRepository workingOvertimeRepository) {
        this.workingTimeRepository = workingTimeRepository;
        this.periodRepository = periodRepository;
        this.workingTimeItemRepository = workingTimeItemRepository;
        this.workingOvertimeRepository = workingOvertimeRepository;
    }

    // add workhour
    public void addWorkingTime(WorkingTime workingTime){
        int shiftType = 2;
        if(workingTime.getDateFrom().equals(workingTime.getDateTo())) {
            shiftType = 1;
        }
        workingTime.setShiftId(shiftType);

        // check period status
        if (periodRepository.isPeriodStatusO(workingTime.getDateFrom().getMonthValue(), workingTime.getDateFrom().getYear())) {
            workingTime.setStatus("O");
            workingTimeRepository.insertWorkingTimeByAppUserId(workingTime);
        }
    }

    public void updateWorkingTime(WorkingTime workingTime) {
        // get working time by id
        if (workingTimeRepository.existsByIdWorkTime(workingTime.getIdWorkTime()) &&
                periodRepository.isPeriodStatusO(workingTime.getDateFrom().getMonthValue(), workingTime.getDateFrom().getYear())) {
            workingTime.setStatus("O");
            workingTimeRepository.updateWorkingTimeByIdWorkTime(workingTime);
        }
    }



    public void deleteWorkingTimeById(int idWorkTime) {
        // exists by id
        if (workingTimeRepository.existsByIdWorkTime(idWorkTime)) {
            // set status to S
            workingTimeRepository.setWorkingTimeStatusToSByIdWorkTime(idWorkTime);
        }
    }

    public Map<LocalDate, List<WorkingTimeDTO>> getWorkingHoursForMonth(int appUserId, int year, int month) {
        List<WorkingTime> workingTimes = workingTimeRepository.findByAppUserIdAndMonthAndYear(appUserId, month, year);
        Map<LocalDate, List<WorkingTimeDTO>> workingHoursMap = new HashMap<>();

        for (WorkingTime wt : workingTimes) {
            int id = wt.getIdWorkTime();
            LocalDate startDate = wt.getDateFrom();
            LocalDate endDate = wt.getDateTo();
            int startHour = wt.getHoursFrom();
            int endHour = wt.getHoursTo();

            // Ako smjena prelazi u sljedeći dan
            if (!startDate.equals(endDate)) {
                // Dodaj prvi dan smjene
                workingHoursMap.computeIfAbsent(startDate, k -> new ArrayList<>())
                        .add(new WorkingTimeDTO(id, startDate, startHour, 24, 24 - startHour));

                // Dodaj drugi dan smjene
                workingHoursMap.computeIfAbsent(endDate, k -> new ArrayList<>())
                        .add(new WorkingTimeDTO(id, endDate, 0, endHour, endHour));
            } else {
                // Ako smjena ne prelazi u drugi dan, dodaj direktno
                workingHoursMap.computeIfAbsent(startDate, k -> new ArrayList<>())
                        .add(new WorkingTimeDTO(id, startDate, startHour, endHour, endHour - startHour));
            }
        }
        System.out.println(workingHoursMap);
        return workingHoursMap;
    }

    // get period by month and year
    public Period getByMonthAndYear(int month, int year) {
        return periodRepository.findByMonthAndYear(month, year);
    }

    // overtime

    // dohvatiti kljuc id work time i spremiti ga u overtime
    public void addOvertimeWorkHour(WorkingOvertime workingOvertime) {

        // da li postoji stavka workTimeItem ? sa app user id i datumom from
            // get work time item by app user id and date
        List<WorkingTimeItem> workingTimeItems = workingTimeItemRepository.findItemByAppUserIdAndDate(
                workingOvertime.getAppUserId(), workingOvertime.getDateFrom());

        // ako postoji uzmi kljuc
        if(workingTimeItems.size() > 0) {
            WorkingTimeItem workingTimeItem = workingTimeItems.get(0);
            int workTimeId = workingTimeItem.getWorkTimeId();
            workingOvertime.setIdWorkTime(workTimeId);

            if(workingTimeRepository.existsByAppUserIdAndDateFrom(workingOvertime.getAppUserId(),
                    workingOvertime.getDateFrom(), workingOvertime.getIdWorkTime())) {
                // provjeriti da se sati ne preklapaju
                // overlapping times
                boolean isOverlapping = false;
                for(WorkingTimeItem wti: workingTimeItems) {
                    // big decimal ????
                    if ((BigDecimal.valueOf(wti.getHoursFrom()).compareTo(workingOvertime.getHoursFrom()) < 0 &&
                            BigDecimal.valueOf(wti.getHoursTo()).compareTo(workingOvertime.getHoursFrom()) > 0) ||
                        (BigDecimal.valueOf(wti.getHoursTo()).compareTo(workingOvertime.getHoursFrom()) > 0 &&
                            BigDecimal.valueOf(wti.getHoursTo()).compareTo(workingOvertime.getHoursTo()) < 0)) {
                        // wti.getHoursTo falls within the range [workingOvertime.getHoursFrom(), workingOvertime.getHoursTo()]
                        // Code for overlapping logic here
                        isOverlapping = true;
                        break;
                    }

                }

                // calculate total hours
                if(!isOverlapping) {
                    workingOvertime.setTotalHours(TimeConverterHelper.calculateTotalHours(
                            workingOvertime.getHoursFrom(), workingOvertime.getHoursTo()));

                    // check if overtime exists by app user id and date and id work time
                    workingOvertimeRepository.insert(workingOvertime);
                }
            }

        }

    }

    // onemoguciti unos preklapanja

    // insert overtime

    // automate overtime



}
