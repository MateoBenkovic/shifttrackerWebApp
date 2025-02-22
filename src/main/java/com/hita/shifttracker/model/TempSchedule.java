package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("temp_schedule_per_employee")
@Data
public class TempSchedule {

    @Column("id")
    private int id;

    @Column("work_date")
    private LocalDate workDate;

}
