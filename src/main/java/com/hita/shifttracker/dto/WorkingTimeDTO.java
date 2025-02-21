package com.hita.shifttracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkingTimeDTO {
    private LocalDate date;
    private Integer startHour;
    private Integer endHour;
    private Integer totalHours;

    public WorkingTimeDTO(LocalDate date, int startHour, int endHour, int totalHours) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.totalHours = totalHours;
    }
}
