package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "vw_working_time_user_wt_cal")
@Data
public class WorkingTimeUserWtCalView {

    @Column("godina")
    private int year;
    @Column("mjesec")
    private int month;

    @Column("us_code")
    private String appUserCode;
    @Column("id_v_r")
    private int idWorkTypes;
    @Column("naziv_vr_rada")
    private String workTypeName;

    @Column("app_user_id")
    private int appUserId;


    @Column ("1")
    private int day01;
    @Column ("2")
    private int day02;
    @Column ("3")
    private int day03;
    @Column ("4")
    private int day04;
    @Column ("5")
    private int day05;
    @Column ("6")
    private int day06;
    @Column ("7")
    private int day07;
    @Column ("8")
    private int day08;
    @Column ("9")
    private int day09;
    @Column ("10")
    private int day10;
    @Column ("11")
    private int day11;
    @Column ("12")
    private int day12;
    @Column ("13")
    private int day13;
    @Column ("14")
    private int day14;
    @Column ("15")
    private int day15;
    @Column ("16")
    private int day16;
    @Column ("17")
    private int day17;
    @Column ("18")
    private int day18;
    @Column ("19")
    private int day19;
    @Column ("20")
    private int day20;
    @Column ("21")
    private int day21;
    @Column ("22")
    private int day22;
    @Column ("23")
    private int day23;
    @Column ("24")
    private int day24;
    @Column ("25")
    private int day25;
    @Column ("26")
    private int day26;
    @Column ("27")
    private int day27;
    @Column ("28")
    private int day28;
    @Column ("29")
    private int day29;
    @Column ("30")
    private int day30;
    @Column ("31")
    private int day31;

    @Column ("Ukupno")
    private int total;
}
