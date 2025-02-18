package com.hita.shifttracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkingTimeDTO {
    private LocalDate date;
    private Integer startHour;
    private Integer endHour;
    private Integer totalHours;

    public WorkingTimeDTO(LocalDate date) {
        this.date = date;
        this.startHour = null;
        this.endHour = null;
        this.totalHours = 0;
    }
}
