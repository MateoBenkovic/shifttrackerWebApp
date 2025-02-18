package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/*
* doktor
* sestra
* vozac
 */
@Table(name = "team_role")
@Data
public class TeamRole {

    @Id
    @Column("id")
    private int teamRoleId;

    @Column("name")
    private String teamRoleName;

    @Column("order_number")
    private int teamRoleOrderNumber;
}
