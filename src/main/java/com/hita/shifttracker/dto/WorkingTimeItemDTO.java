package com.hita.shifttracker.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WorkingTimeItemDTO {

    private int appUserId;
    private String workTypeName;
    private String workTypeNum;
    private String accountNum;
    private LocalDate date;
    private Integer workTypeCode;
    private Integer hoursFrom;
    private Integer hoursTo;
    private Integer totalHours;

}
