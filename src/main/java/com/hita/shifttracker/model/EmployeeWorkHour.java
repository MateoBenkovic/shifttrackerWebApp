package com.hita.shifttracker.model;

import com.hita.shifttracker.model.shared.AppUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class EmployeeWorkHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp workHour;
    private AppUser appUser;

    public EmployeeWorkHour() {
    }

    public EmployeeWorkHour(int id, Timestamp workHour, AppUser appUser) {
        this.id = id;
        this.workHour = workHour;
        this.appUser = appUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getWorkHour() {
        return workHour;
    }

    public void setWorkHour(Timestamp workHour) {
        this.workHour = workHour;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
