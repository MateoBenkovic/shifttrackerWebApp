package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vw_working_time_user_wt_cal")
@Data
public class WorkingTimeUserWtCalView {

    @EmbeddedId
    private WorkingTimeUserWtCalViewId id;

    @Column(name = "godina")
    private int year;
    @Column(name = "mjesec")
    private int month;

    @Column(name = "us_code", length = 5)
    private String appUserCode;
    @Column(name = "id_v_r")
    private int idWorkTypes;
    @Column(name = "naziv_vr_rada")
    private String workTypeName;

    @Column(name = "app_user_id")
    private int appUserId;


    @Column (name = "1")
    private String day01;
    @Column (name = "2")
    private String day02;
    @Column (name = "3")
    private String day03;
    @Column (name = "4")
    private String day04;
    @Column (name = "5")
    private String day05;
    @Column (name = "6")
    private String day06;
    @Column (name = "7")
    private String day07;
    @Column (name = "8")
    private String day08;
    @Column (name = "9")
    private String day09;
    @Column (name = "10")
    private String day10;
    @Column (name = "11")
    private String day11;
    @Column (name = "12")
    private String day12;
    @Column (name = "13")
    private String day13;
    @Column (name = "14")
    private String day14;
    @Column (name = "15")
    private String day15;
    @Column (name = "16")
    private String day16;
    @Column (name = "17")
    private String day17;
    @Column (name = "18")
    private String day18;
    @Column (name = "19")
    private String day19;
    @Column (name = "20")
    private String day20;
    @Column (name = "21")
    private String day21;
    @Column (name = "22")
    private String day22;
    @Column (name = "23")
    private String day23;
    @Column (name = "24")
    private String day24;
    @Column (name = "25")
    private String day25;
    @Column (name = "26")
    private String day26;
    @Column (name = "27")
    private String day27;
    @Column (name = "28")
    private String day28;
    @Column (name = "29")
    private String day29;
    @Column (name = "30")
    private String day30;
    @Column (name = "31")
    private String day31;

    @Column (name = "Ukupno")
    private String total;
}
