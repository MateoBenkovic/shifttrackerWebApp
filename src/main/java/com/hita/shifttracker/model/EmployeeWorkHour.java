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

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    private int totalHours;

    public EmployeeWorkHour(LocalDateTime startTime, LocalDateTime endTime, int totalHours) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHours = totalHours;
    }

    public EmployeeWorkHour() {
    }
}
