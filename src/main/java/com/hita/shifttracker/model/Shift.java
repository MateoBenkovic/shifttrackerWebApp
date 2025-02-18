package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "shift")
@Data
public class Shift {

    @Id
    @Column("id")
    private int id;
    @Column("name")
    private String name;
    @Column("shift_id")
    private int shiftId;
    @Column("start_time")
    private int startTime;
    @Column("end_time")
    private int endTime;

}
