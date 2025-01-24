package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
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

    @OneToMany
    @JoinColumn(name = "app_user_id")
    private List<AppUser> employees;

    private int schedulePerMonthId;

    public ScheduleTeamPerShift() {
    }

    public ScheduleTeamPerShift(int id, Timestamp date, Shift shift, Team team, List<AppUser> employees,
                                int schedulePerMonthId) {
        this.id = id;
        this.date = date;
        this.shift = shift;
        this.team = team;
        this.employees = employees;
        this.schedulePerMonthId = schedulePerMonthId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<AppUser> getEmployees() {
        return employees;
    }

    public void setEmployees(List<AppUser> employees) {
        this.employees = employees;
    }

    public int getSchedulePerMonthId() {
        return schedulePerMonthId;
    }

    public void setSchedulePerMonthId(int schedulePerMonthId) {
        this.schedulePerMonthId = schedulePerMonthId;
    }
}
