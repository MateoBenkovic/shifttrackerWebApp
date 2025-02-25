package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "working_overtime")
@Data
public class WorkingOvertime {

    @Id
    @Column("id_overtime")
    private int idOvertime;
    @Column("id_work_time")
    private int idWorkTime;
    @Column("app_user_id")
    private int appUserId;
    @Column("date_from")
    private LocalDate dateFrom;
    @Column("hours_from")
    private BigDecimal hoursFrom;
    @Column("date_to")
    private LocalDate dateTo;
    @Column("hours_to")
    private BigDecimal hoursTo;

    @Column("total_hours")
    private BigDecimal totalHours;

    @Column("shift_id")
    private int shiftId;

    // private int uidInsUpd;
    // private Timestamp createdAt;
    // private Timestamp updatedAt;

    @Column("status")
    private String status;
}
