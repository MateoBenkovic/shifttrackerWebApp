package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "holiday")
@Data
public class Holiday {

    @Id
    @Column("id")
    private int holidayId;

    @Column("name")
    private String holidayName;

    @Column("date")
    private LocalDate holidayDate;
}
