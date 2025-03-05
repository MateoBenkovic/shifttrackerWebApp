package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;

@Table("leave_record")
@Data
public class LeaveRecord {

    @Id
    @Column("id_leave")
    private int idLeave;
    @Column("app_user_id")
    private int appUserId;
    @Column("work_type_other_id")
    private int workTypeOtherId;
    @Column("date_from")
    private LocalDate dateFrom;
    @Column("date_to")
    private LocalDate dateTo;
    @Column("total_days")
    private int totalDays;
    @Column("hours_per_day")
    private int hoursPerDay;
    @Column("total_hours")
    private int totalHours;
    @Column("uid_ins_upd")
    private int uidInsUpd;
    @Column("created_at")
    private Timestamp createdAt;
    @Column("updated_at")
    private Timestamp updatedAt;
    @Column("status")
    private String status;
    @Column("comment_leave")
    private String commentLeave;
}
