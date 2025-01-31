package com.hita.shifttracker.service;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class WorkTimeService {

    LocalTime morningStart = LocalTime.of(6, 0);  // 6 AM
    LocalTime noonStart = LocalTime.of(14, 0);    // 2 PM
    LocalTime nightStart = LocalTime.of(22, 0);   // 10 PM
    LocalTime nightEnd = LocalTime.of(6, 0);
}
