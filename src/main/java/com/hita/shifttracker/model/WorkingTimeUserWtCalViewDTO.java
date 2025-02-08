package com.hita.shifttracker.model;

import jakarta.persistence.Column;
import lombok.*;

public class WorkingTimeUserWtCalViewDTO {


    private int year;
    private int month;

    @Column(name = "naziv_vr_rada")
    private String workTypeName;
    private int appUserId;

    public WorkingTimeUserWtCalViewDTO(int year, int month, String workTypeName, int appUserId) {
        this.year = year;
        this.month = month;
        this.workTypeName = workTypeName;
        this.appUserId = appUserId;
    }

    public WorkingTimeUserWtCalViewDTO(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public WorkingTimeUserWtCalViewDTO() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
}
