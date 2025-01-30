package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class EmployeeWorkHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime date;
    private int hoursTotal;

    private int hoursMorning;
    private int hoursNoon;
    private int hoursNight;

    private int holiday;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    private int totalHours;

    public EmployeeWorkHour(LocalDateTime date, int totalHours, AppUser appUser) {
        this.date = date;
        this.totalHours = totalHours;
        this.appUser = appUser;
    }
}
