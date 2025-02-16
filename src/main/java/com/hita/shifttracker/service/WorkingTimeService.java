package com.hita.shifttracker.service;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.WorkingTime;
import com.hita.shifttracker.repository.WorkingTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Map<LocalDate, WorkingTime> getWorkingTimeForMonth(AppUser appUser, int month, int year){
        int appUserId = appUser.getId();
        List<WorkingTime> workingTimes = workingTimeRepository.findAllByAppUserIdAndMonthAndYear(appUserId, month, year);
        Map<LocalDate, WorkingTime> workingHoursMap = new HashMap<>();

        for (WorkingTime wt : workingTimes) {
            LocalDate startDate = wt.getDateFrom();
            LocalDate endDate = wt.getDateTo();
            int startHour = wt.getHoursFrom();
            int endHour = wt.getHoursTo();

            // Ako smjena prelazi u sljedeći dan
            if (!startDate.equals(endDate)) {
                // Prvi dan (od startHour do 24:00)
                if (!workingHoursMap.containsKey(startDate)) {
                    workingHoursMap.put(startDate, new WorkingTime(startDate));
                }
                WorkingTime startDay = workingHoursMap.get(startDate);
                startDay.setHoursFrom(startHour);
                startDay.setHoursTo(24); // Završava u 24:00
                startDay.setTotalHours(24 - startHour); // Ukupni sati do kraja dana

                // Drugi dan (od 00:00 do endHour)
                if (!workingHoursMap.containsKey(endDate)) {
                    workingHoursMap.put(endDate, new WorkingTime(endDate));
                }
                WorkingTime nextDay = workingHoursMap.get(endDate);
                nextDay.setHoursFrom(0);
                nextDay.setHoursTo(endHour);
                nextDay.setTotalHours(endHour); // Ukupni sati od ponoći do završetka
            }
            // Ako smjena ne prelazi u sljedeći dan
            else {
                if (!workingHoursMap.containsKey(startDate)) {
                    workingHoursMap.put(startDate, new WorkingTime(startDate));
                }
                WorkingTime sameDay = workingHoursMap.get(startDate);
                sameDay.setHoursFrom(startHour);
                sameDay.setHoursTo(endHour);
                sameDay.setTotalHours(endHour - startHour);
            }
        }

        return workingHoursMap;
    }

}
