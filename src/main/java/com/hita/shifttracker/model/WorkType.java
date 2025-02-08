package com.hita.shifttracker.model;

import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

public class WorkType {

    private int id;

    private String workTypeNumber;
    private String workTypeName;
    private String dayIndicator;
    private String holidayIndicator;
    private String shiftIndicator;
    private String accountNumber;
    private BigDecimal coefficient;
    private String overtimeIndicator;
}
