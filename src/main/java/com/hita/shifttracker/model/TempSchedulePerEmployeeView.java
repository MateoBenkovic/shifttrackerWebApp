package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("vw_temp_schedule_per_employee")
@Data
public class TempSchedulePerEmployeeView {

    @Column("category")
    private String category;

    @Column("year")
    private int year;
    @Column("month")
    private int month;
    @Column("app_user_id")
    private int appUserId;

    @Column("1")
    private String day01;
    @Column("2")
    private String day02;
    @Column("3")
    private String day03;
    @Column("4")
    private String day04;
    @Column("5")
    private String day05;
    @Column("6")
    private String day06;
    @Column("7")
    private String day07;
    @Column("8")
    private String day08;
    @Column("9")
    private String day09;
    @Column("10")
    private String day10;
    @Column("11")
    private String day11;
    @Column("12")
    private String day12;
    @Column("13")
    private String day13;
    @Column("14")
    private String day14;
    @Column("15")
    private String day15;
    @Column("16")
    private String day16;
    @Column("17")
    private String day17;
    @Column("18")
    private String day18;
    @Column("19")
    private String day19;
    @Column("20")
    private String day20;
    @Column("21")
    private String day21;
    @Column("22")
    private String day22;
    @Column("23")
    private String day23;
    @Column("24")
    private String day24;
    @Column("25")
    private String day25;
    @Column("26")
    private String day26;
    @Column("27")
    private String day27;
    @Column("28")
    private String day28;
    @Column("29")
    private String day29;
    @Column("30")
    private String day30;
    @Column("31")
    private String day31;

}
