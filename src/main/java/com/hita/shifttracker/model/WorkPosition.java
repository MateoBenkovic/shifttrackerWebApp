package com.hita.shifttracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("work_positions")
public class WorkPosition {

    @Id
    @Column("id")
    private int id;
    @Column("position_code")
    private String workPositionCode;
    @Column("position_name")
    private String workPositionName;
    @Column("coefficient")
    private BigDecimal coefficient;
}
