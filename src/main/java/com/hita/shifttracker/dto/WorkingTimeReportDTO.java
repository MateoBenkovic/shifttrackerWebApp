package com.hita.shifttracker.dto;

import lombok.Data;

@Data
public class WorkingTimeReportDTO {
    private String firstName;
    private String lastName;
    private String ispostava;
    private int month;
    private int year;
    private int redovanRad;
    private Double prekovremeniRad;
    private int odsustva;
    private Double ukupno;
}
