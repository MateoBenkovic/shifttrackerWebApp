package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String oib;

    @ManyToOne
    @JoinColumn(name = "org_unit_id")
    private OrgUnit orgUnit;

    private String email;
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "app_role_id")
    private  AppRole appRole;

    @ManyToOne
    @JoinColumn(name = "team_role_id")
    private TeamRole teamRole;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "schedule_team_per_shift_id")
    private ScheduleTeamPerShift scheduleTeamPerShift;
}
