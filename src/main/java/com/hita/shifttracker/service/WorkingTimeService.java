package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.WorkingTimeDTO;
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

    public void deleteWorkingTimeByAppUserIdAndDateFrom(int appUserId, LocalDate dateFrom) {
        if(workingTimeRepository.existsByAppUserIdAndDateFrom(appUserId, dateFrom)) {
            workingTimeRepository.deleteByAppUserIdAndDateFrom(appUserId, dateFrom);
        }
    }

    public Map<LocalDate, WorkingTimeDTO> getWorkingHoursForMonth(int appUserId, int year, int month) {
        List<WorkingTime> workingTimes = workingTimeRepository.findByAppUserIdAndMonthAndYear(appUserId, month, year);
        Map<LocalDate, WorkingTimeDTO> workingHoursMap = new HashMap<>();

        for (WorkingTime wt : workingTimes) {
            LocalDate startDate = wt.getDateFrom();
            LocalDate endDate = wt.getDateTo();
            int startHour = wt.getHoursFrom();
            int endHour = wt.getHoursTo();

            // Ako smjena prelazi u sljedeći dan
            if (!startDate.equals(endDate)) {
                // Prvi dan (od startHour do 24:00)
                if (!workingHoursMap.containsKey(startDate)) {
                    workingHoursMap.put(startDate, new WorkingTimeDTO(startDate));
                }
                WorkingTimeDTO startDay = workingHoursMap.get(startDate);
                startDay.setStartHour(startHour);
                startDay.setEndHour(24); // Završava u 24:00
                startDay.setTotalHours(24 - startHour); // Ukupni sati do kraja dana

                // Drugi dan (od 00:00 do endHour)
                if (!workingHoursMap.containsKey(endDate)) {
                    workingHoursMap.put(endDate, new WorkingTimeDTO(endDate));
                }
                WorkingTimeDTO nextDay = workingHoursMap.get(endDate);
                nextDay.setStartHour(0);
                nextDay.setEndHour(endHour);
                nextDay.setTotalHours(endHour); // Ukupni sati od ponoći do završetka
            }
            // Ako smjena ne prelazi u sljedeći dan
            else {
                if (!workingHoursMap.containsKey(startDate)) {
                    workingHoursMap.put(startDate, new WorkingTimeDTO(startDate));
                }
                WorkingTimeDTO sameDay = workingHoursMap.get(startDate);
                sameDay.setStartHour(startHour);
                sameDay.setEndHour(endHour);
                sameDay.setTotalHours(endHour - startHour);
            }
        }

        return workingHoursMap;
    }


}
