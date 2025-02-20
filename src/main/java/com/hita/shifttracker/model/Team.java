package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/*
* Tim 1
* Tim 2
* Tim 3
* */

@Table(name = "team")
@Data
public class Team {

    @Id
    @Column("id")
    private int teamId;

    @Column("name")
    private String teamName;

    @Column("order_number")
    private String teamOrderNumber;

    @Column("type")
    private int teamType;
}
