package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;



@Table(name = "work_types")
@Data
public class WorkType {

    @Id
    @Column("id")
    private int id;

    @Column("work_type_num")
    private String workTypeNumber;

    @Column("work_type_name")
    private String workTypeName;

    @Column("day_indicator")
    private String dayIndicator;

    @Column("holiday_indicator")
    private String holidayIndicator;

    @Column("shift_indicator")
    private String shiftIndicator;

    @Column("account_num")
    private String accountNumber;

    @Column("coefficient")
    private BigDecimal coefficient;

    @Column("overtime_indicator")
    private String overtimeIndicator;

}
