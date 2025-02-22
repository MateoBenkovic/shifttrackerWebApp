package com.hita.shifttracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkingTimeDTO {
    private int id;
    private LocalDate date;
    private Integer startHour;
    private Integer endHour;
    private Integer totalHours;

    public WorkingTimeDTO(int id, LocalDate date, int startHour, int endHour, int totalHours) {
        this.id = id;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.totalHours = totalHours;
    }
}
