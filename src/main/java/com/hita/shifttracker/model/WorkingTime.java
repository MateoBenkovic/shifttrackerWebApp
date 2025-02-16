package com.hita.shifttracker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "working_time")
@Data
public class WorkingTime {

    @Id
    @Column("id_work_time")
    private int idWorkTime;

    @Column("app_user_id")
    private int appUserId;


    @Column("date_from")
    private LocalDate dateFrom;

    @Column("hours_from")
    private int hoursFrom;

    @Column("date_to")
    private LocalDate dateTo;

    @Column("hours_to")
    private int hoursTo;

    @Column("total_hours")
    private int totalHours;

    @Column("sched_id")
    private int schedId;

    @Column("shift_id")
    private int shiftId;

}
