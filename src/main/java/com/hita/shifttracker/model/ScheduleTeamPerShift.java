package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class ScheduleTeamPerShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp date;

    @OneToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany
    @JoinTable(name = "schedule_employees",
               joinColumns = @JoinColumn(name = "schedule_id"),
               inverseJoinColumns = @JoinColumn(name = "app_user_id"))
    private List<AppUser> employees;
    //OneToMany
    //MappedBySchedulePerShift

    private int schedulePerMonthId; //dodati vezu
}
