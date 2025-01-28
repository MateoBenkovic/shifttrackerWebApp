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

    private LocalDateTime startDateTime;
    private LocalDateTime endLocalDateTime;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    private int totalHours;

    public EmployeeWorkHour(LocalDateTime startDateTime, LocalDateTime endLocalDateTime, int totalHours, AppUser appUser) {
        this.startDateTime = startDateTime;
        this.endLocalDateTime = endLocalDateTime;
        this.totalHours = totalHours;
        this.appUser = appUser;
    }
}
