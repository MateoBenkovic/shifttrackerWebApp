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

        // check if exists
        WorkingTime wt = workingTimeRepository.findWorkingTimeByAppUserIdAndDateFrom(workingTime.getAppUserId(), workingTime.getDateFrom());
        if(wt == null) {
            workingTimeRepository.insertWorkingTime(workingTime.getAppUserId(), workingTime.getDateFrom(),
                    workingTime.getHoursFrom(), workingTime.getDateTo(), workingTime.getHoursTo(),
                    workingTime.getTotalHours(), workingTime.getShiftId());
        } else {
            workingTimeRepository.updateWorkingTimeByAppUserId(workingTime);
        }


    }

}
