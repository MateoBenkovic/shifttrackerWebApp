package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.sql.In;

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
    private Integer day01;
    @Column ("2")
    private Integer day02;
    @Column ("3")
    private Integer day03;
    @Column ("4")
    private Integer day04;
    @Column ("5")
    private Integer day05;
    @Column ("6")
    private Integer day06;
    @Column ("7")
    private Integer day07;
    @Column ("8")
    private Integer day08;
    @Column ("9")
    private Integer day09;
    @Column ("10")
    private Integer day10;
    @Column ("11")
    private Integer day11;
    @Column ("12")
    private Integer day12;
    @Column ("13")
    private Integer day13;
    @Column ("14")
    private Integer day14;
    @Column ("15")
    private Integer day15;
    @Column ("16")
    private Integer day16;
    @Column ("17")
    private Integer day17;
    @Column ("18")
    private Integer day18;
    @Column ("19")
    private Integer day19;
    @Column ("20")
    private Integer day20;
    @Column ("21")
    private Integer day21;
    @Column ("22")
    private Integer day22;
    @Column ("23")
    private Integer day23;
    @Column ("24")
    private Integer day24;
    @Column ("25")
    private Integer day25;
    @Column ("26")
    private Integer day26;
    @Column ("27")
    private Integer day27;
    @Column ("28")
    private Integer day28;
    @Column ("29")
    private Integer day29;
    @Column ("30")
    private Integer day30;
    @Column ("31")
    private Integer day31;

    @Column ("Ukupno")
    private int total;
}
