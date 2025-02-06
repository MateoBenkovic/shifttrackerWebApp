package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;
// doktor sestra vozac
@Entity
@Table(name = "team_role")
@Data
public class TeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int teamRoleId;

    @Column(name = "name")
    private String teamRoleName;

    @Column(name = "order_number")
    private int teamRoleOrderNumber;
}
