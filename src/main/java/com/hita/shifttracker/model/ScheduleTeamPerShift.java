package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class ScheduleTeamPerShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team teamId;

    @ManyToOne
    @JoinColumn(name = "employee1_id")
    private AppUser employee1Id;

    @ManyToOne
    @JoinColumn(name = "employee2_id")
    private AppUser employee2Id;

    @ManyToOne
    @JoinColumn(name = "employee3_id")
    private AppUser employee3Id;

    private int schedulePerMonthId;

}
