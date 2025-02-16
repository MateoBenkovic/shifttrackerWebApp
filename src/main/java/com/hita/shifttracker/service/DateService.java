package com.hita.shifttracker.service;

import com.hita.shifttracker.repository.DateRepository;
import org.springframework.stereotype.Service;

@Service
public class DateService {

    private final DateRepository dateRepository;

    public DateService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public int getCurrentYearFromDatabase() {
        return dateRepository.getCurrentYearFromDatabase();
    }

    public int getCurrentMonthFromDatabase() {
        return dateRepository.getCurrentMonthFromDatabase();
    }
}
