package com.hita.shifttracker.model;

import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "work_types")
@Data
public class WorkType {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "work_type_num", nullable = false, length = 3, unique = true)
    private String workTypeNumber;

    @Column(name = "work_type_name", nullable = false, length = 50)
    private String workTypeName;

    @Column(name = "day_indicator", nullable = false, length = 1)
    private String dayIndicator;

    @Column(name = "holiday_indicator", nullable = false, length = 1)
    private String holidayIndicator;

    @Column(name = "shift_indicator", nullable = false, length = 1)
    private String shiftIndicator;

    @Column(name = "account_num", nullable = false, length = 6, unique = true)
    private String accountNumber;

    @Column(name = "coefficient", nullable = false)
    private BigDecimal coefficient;

    @Column(name = "overtime_indicator", nullable = false, length = 1)
    private String overtimeIndicator;

}
