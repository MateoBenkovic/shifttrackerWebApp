package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("period_radnik")
@Data
public class PeriodRadnik {

    @Id
    @Column("id")
    private int id;
    @Column("app_user_id")
    private int appUserId;
    @Column("period_id")
    private int periodId;
    @Column("status")
    private String status;
    @Column("inserted_at")
    private Timestamp insertedAt;
    @Column("updated_at")
    private Timestamp updatedAt;
    @Column("uid_ins_upd")
    private int uidInsUpd;
    @Column("year")
    private int year;
    @Column("month")
    private int month;

}
