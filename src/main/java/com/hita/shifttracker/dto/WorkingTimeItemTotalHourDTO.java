package com.hita.shifttracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkingTimeItemTotalHourDTO {

    private int appUserId;
    private LocalDate date;
    private int totalHours;
    private int hoursFrom;
    private int hoursTo;
}
