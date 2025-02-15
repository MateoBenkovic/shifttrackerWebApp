package com.hita.shifttracker.service;

import com.hita.shifttracker.model.WorkingTime;
import com.hita.shifttracker.repository.WorkingTimeRepository;
import org.springframework.stereotype.Service;

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

}
