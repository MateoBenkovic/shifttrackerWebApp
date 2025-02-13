package com.hita.shifttracker.service;

import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.model.Shift;
import com.hita.shifttracker.model.WorkingTime;
import com.hita.shifttracker.repository.ShiftRepository;
import com.hita.shifttracker.repository.WorkingTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkingTimeService {

    private final WorkingTimeRepository workingTimeRepository;
    private final ShiftRepository shiftRepository;

    public WorkingTimeService(WorkingTimeRepository workingTimeRepository, ShiftRepository shiftRepository) {
        this.workingTimeRepository = workingTimeRepository;
        this.shiftRepository = shiftRepository;
    }

    // add workhour
    public void addWorkingTime(WorkingTime workingTime){

        workingTimeRepository.insertOrUpdateWorkingTime(workingTime.getAppUserId(), workingTime.getDateFrom(),
                workingTime.getHoursFrom(), workingTime.getDateTo(), workingTime.getHoursTo(),
                workingTime.getTotalHours(), workingTime.getShiftId());
    }

    public List<WorkingTime> getWorkingTimeByDate(int appUserId, LocalDate dateFrom) {
        List<WorkingTime> workingTimes = workingTimeRepository.findWorkingTimeByAppUserIdAndDate(appUserId, dateFrom);
        return workingTimes;
    }

    public List<WorkingTime> getWorkingTimeByAppUserIdAndMonth(AppUser appUser, LocalDate date){

        int appUserId = appUser.getId();
        int month = date.getMonthValue();
        List<WorkingTime> workingTimes = workingTimeRepository.findByAppUserIdAndMonth(appUserId, month);

        return workingTimes;
    }

}
