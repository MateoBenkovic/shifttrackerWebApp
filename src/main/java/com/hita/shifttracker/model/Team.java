package com.hita.shifttracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "team")
@Data
public class Team {

    @Id
    @Column(name = "id")
    private int teamId;

    @Column(name = "name")
    private String teamName;

    @Column(name = "order_number")
    private String teamOrderNumber;

    @Column(name = "type")
    private int teamType;
}
