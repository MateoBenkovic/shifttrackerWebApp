package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;


@Table("period")
@Data
public class Period {

    @Column("year")
    private int year;

    @Column("month")
    private int month;

    @Column("month_name")
    private String monthName;

    @Column("status")
    private String status;

    @Column("closing_date")
    private LocalDate closingDate;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("total_hours")
    private int totalHours;

    @Column("payroll_base_monthly")
    private BigDecimal payrollBaseMonthly;

    @Column("payroll_base_hourly")
    private BigDecimal payrollBaseHourly;

    @Column("working_days")
    private int workingDays;
}
