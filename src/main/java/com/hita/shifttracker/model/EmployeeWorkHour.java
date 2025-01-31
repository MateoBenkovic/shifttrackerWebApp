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
    private int hourStart;
    private int hourEnd;

    private int hoursTotalPerDay;

    private int hoursMorning;
    private int hoursNoon;
    private int hoursNight;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
}
