package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("temp_schedule_per_employee")
@Data
public class TempSchedulePerEmployee {

    @Id
    @Column("id")
    private int id;

    @Column("work_date")
    private LocalDate workDate;

    @Column("hours_from")
    private int hoursFrom;

    @Column("hours_to")
    private int hoursTo;

    @Column("app_user_id")
    private int appUserId;

    @Column("shift_id")
    private int shiftId;

    @Column("stavka")
    private int stavka;
}
