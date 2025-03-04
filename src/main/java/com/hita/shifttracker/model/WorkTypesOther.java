package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "work_types_other")
@Data
public class WorkTypesOther {

    @Id
    @Column("id")
    private int id;
    @Column("work_type_num")
    private String workTypeNum;
    @Column("work_type_name")
    private String workTypeName;
    @Column("account_num")
    private String accountNum;
    @Column("coefficient")
    private BigDecimal coefficient;

}
