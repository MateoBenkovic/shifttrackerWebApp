package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "working_time_item")
@Data
public class WorkingTimeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_work_time_item")
    private int idWorkTimeItem;

    @ManyToOne
    @JoinColumn(name = "work_time_id", referencedColumnName = "id_work_time")
    private WorkingTime workTime;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "work_type_code", referencedColumnName = "id")
    private WorkType workType;

    @Column(name = "hours_from")
    private int hoursFrom;

    @Column(name = "hours_to")
    private int hoursTo;

    @Column(name = "total_hours")
    private int totalHours;

    @Column(name = "item_number")
    private int itemNumber;

}
