package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.WorkingTimeDTO;
import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.WorkingTime;
import com.hita.shifttracker.repository.WorkingTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkingTimeService {

    private final WorkingTimeRepository workingTimeRepository;

    public WorkingTimeService(WorkingTimeRepository workingTimeRepository) {
        this.workingTimeRepository = workingTimeRepository;
    }

    // add workhour
    public void addWorkingTime(WorkingTime workingTime){
        // check if exists
        if(workingTimeRepository.existsByAppUserIdAndDateFrom(workingTime.getAppUserId(), workingTime.getDateFrom())) {
            workingTimeRepository.updateWorkingTimeByAppUserId(workingTime);
        } else {
            workingTimeRepository.insertWorkingTimeByAppUserId(workingTime);
        }
    }

    public void deleteWorkingTimeByAppUserIdAndDateFrom(int appUserId, LocalDate dateFrom) {
        if(workingTimeRepository.existsByAppUserIdAndDateFrom(appUserId, dateFrom)) {
            workingTimeRepository.deleteByAppUserIdAndDateFrom(appUserId, dateFrom);
        }
    }

    public Map<LocalDate, List<WorkingTimeDTO>> getWorkingHoursForMonth(int appUserId, int year, int month) {
        List<WorkingTime> workingTimes = workingTimeRepository.findByAppUserIdAndMonthAndYear(appUserId, month, year);
        Map<LocalDate, List<WorkingTimeDTO>> workingHoursMap = new HashMap<>();

        for (WorkingTime wt : workingTimes) {
            LocalDate startDate = wt.getDateFrom();
            LocalDate endDate = wt.getDateTo();
            int startHour = wt.getHoursFrom();
            int endHour = wt.getHoursTo();

            // Ako smjena prelazi u sljedeći dan
            if (!startDate.equals(endDate)) {
                // Dodaj prvi dan smjene
                workingHoursMap.computeIfAbsent(startDate, k -> new ArrayList<>())
                        .add(new WorkingTimeDTO(startDate, startHour, 24, 24 - startHour));

                // Dodaj drugi dan smjene
                workingHoursMap.computeIfAbsent(endDate, k -> new ArrayList<>())
                        .add(new WorkingTimeDTO(endDate, 0, endHour, endHour));
            } else {
                // Ako smjena ne prelazi u drugi dan, dodaj direktno
                workingHoursMap.computeIfAbsent(startDate, k -> new ArrayList<>())
                        .add(new WorkingTimeDTO(startDate, startHour, endHour, endHour - startHour));
            }
        }
        System.out.println(workingHoursMap);
        return workingHoursMap;
    }



}
