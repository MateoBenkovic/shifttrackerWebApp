package com.hita.shifttracker.dto;

import com.hita.shifttracker.model.OrganizationUnit;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WorkingTimeReportDTO {
    private String firstName;
    private String lastName;
    private BigDecimal orgUnitId;
    private String orgUnitName;
    private int month;
    private int year;
    private BigDecimal redovanRad;
    private BigDecimal prekovremeniRad;
    private BigDecimal odsustva;
    private BigDecimal ukupno;

    public WorkingTimeReportDTO(String firstName, String lastName,BigDecimal orgUnitId, String orgUnitName, int month, int year, BigDecimal redovanRad, BigDecimal prekovremeniRad, BigDecimal odsustva, BigDecimal ukupno) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.orgUnitId = orgUnitId;
        this.orgUnitName = orgUnitName;
        this.month = month;
        this.year = year;
        this.redovanRad = redovanRad;
        this.prekovremeniRad = prekovremeniRad;
        this.odsustva = odsustva;
        this.ukupno = ukupno;

    }

    public WorkingTimeReportDTO() {
    }
}
