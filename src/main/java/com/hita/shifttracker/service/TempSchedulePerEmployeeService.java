package com.hita.shifttracker.service;

import com.hita.shifttracker.model.TempSchedulePerEmployee;
import com.hita.shifttracker.model.TempSchedulePerEmployeeView;
import com.hita.shifttracker.repository.TempSchedulePerEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempSchedulePerEmployeeService {

    private final TempSchedulePerEmployeeRepository tempSchedulePerEmployeeRepository;

    public TempSchedulePerEmployeeService(TempSchedulePerEmployeeRepository tempSchedulePerEmployeeRepository) {
        this.tempSchedulePerEmployeeRepository = tempSchedulePerEmployeeRepository;
    }

    public List<TempSchedulePerEmployee> findAllByAppUserIdAndMonthAndYear(int appUserId, int month, int year) {
        return tempSchedulePerEmployeeRepository.findAllByAppUserIdAndMonthAndYear(appUserId, month, year);
    }

    public List<TempSchedulePerEmployeeView> findAllByAppUserIdAndMonthAndYearPivot(int appUserId, int month, int year) {
        return tempSchedulePerEmployeeRepository.findAllByAppUserIdAndMonthAndYearPivot(appUserId, month, year);
    }
}
