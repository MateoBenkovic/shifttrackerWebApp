package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "working_time_item")
@Data
public class WorkingTimeItem {

    @Id
    @Column("id_work_time_item")
    private int idWorkTimeItem;

    @Column("work_time_id")
    private int workTimeId;

    @Column("app_user_id")
    private int appUserId;

    @Column("date")
    private LocalDate date;

    @Column("work_type_code")
    private int workTypeCode;

    @Column("hours_from")
    private int hoursFrom;

    @Column("hours_to")
    private int hoursTo;

    @Column("total_hours")
    private int totalHours;

    @Column("item_number")
    private int itemNumber;

}
