package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "working_time")
@Data
public class WorkingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_work_time")
    private int idWorkTime;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "hours_from")
    private int hoursFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "hours_to")
    private int hoursTo;

    @Column(name = "total_hours")
    private int totalHours;

    // raspored ????
    //private int schedId;

}
