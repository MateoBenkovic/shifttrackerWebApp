//package com.hita.shifttracker.model;
//
//import jakarta.persistence.Column;
//import lombok.*;
//
//import java.math.BigDecimal;
//
//@Data
//public class WorkingTimeUserWtCalViewDTO {
//
//
//    @Column(name = "godina")
//    private Integer year;
//
//    @Column(name = "mjesec")
//    private Integer month;
//
//    @Column(name = "us_code", length = 5, updatable = false)
//    private String appUserCode;
//    @Column(name = "naziv_vr_rada")
//    private String workTypeName;
//    @Column(name = "id_v_r")
//    private int idWorkTypes;
//
//    @Column(name = "1")
//    private Integer day01;
//    @Column(name = "2")
//    private Integer day02;
//    @Column(name = "3")
//    private Integer day03;
//    @Column(name = "4")
//    private Integer day04;
//    @Column(name = "5")
//    private Integer day05;
//    @Column(name = "6")
//    private Integer day06;
//    @Column(name = "7")
//    private Integer day07;
//    @Column(name = "8")
//    private Integer day08;
//    @Column(name = "9")
//    private Integer day09;
//    @Column(name = "10")
//    private Integer day10;
//    @Column(name = "11")
//    private Integer day11;
//    @Column(name = "12")
//    private Integer day12;
//    @Column(name = "13")
//    private Integer day13;
//    @Column(name = "14")
//    private Integer day14;
//    @Column(name = "15")
//    private Integer day15;
//    @Column(name = "16")
//    private Integer day16;
//    @Column(name = "17")
//    private Integer day17;
//    @Column(name = "18")
//    private Integer day18;
//    @Column(name = "19")
//    private Integer day19;
//    @Column(name = "20")
//    private Integer day20;
//    @Column(name = "21")
//    private Integer day21;
//    @Column(name = "22")
//    private Integer day22;
//    @Column(name = "23")
//    private Integer day23;
//    @Column(name = "24")
//    private Integer day24;
//    @Column(name = "25")
//    private Integer day25;
//    @Column(name = "26")
//    private Integer day26;
//    @Column(name = "27")
//    private Integer day27;
//    @Column(name = "28")
//    private Integer day28;
//    @Column(name = "29")
//    private Integer day29;
//    @Column(name = "30")
//    private Integer day30;
//    @Column(name = "31")
//    private Integer day31;
//
//    @Column(name = "Ukupno")
//    private Integer total;
//
//    public WorkingTimeUserWtCalViewDTO(String workTypeName) {
//        this.workTypeName = workTypeName;
//    }
//
//    public WorkingTimeUserWtCalViewDTO(String workTypeName, Integer day01, Integer day02, Integer day03, Integer day04,
//                                       Integer day05, Integer day06, Integer day07, Integer day08, Integer day09,
//                                       Integer day10, Integer day11, Integer day12, Integer day13, Integer day14,
//                                       Integer day15, Integer day16, Integer day17, Integer day18, Integer day19,
//                                       Integer day20, Integer day21, Integer day22, Integer day23, Integer day24,
//                                       Integer day25, Integer day26, Integer day27, Integer day28, Integer day29,
//                                       Integer day30, Integer day31) {
//        this.workTypeName = workTypeName;
//        this.day01 = day01;
//        this.day02 = day02;
//        this.day03 = day03;
//        this.day04 = day04;
//        this.day05 = day05;
//        this.day06 = day06;
//        this.day07 = day07;
//        this.day08 = day08;
//        this.day09 = day09;
//        this.day10 = day10;
//        this.day11 = day11;
//        this.day12 = day12;
//        this.day13 = day13;
//        this.day14 = day14;
//        this.day15 = day15;
//        this.day16 = day16;
//        this.day17 = day17;
//        this.day18 = day18;
//        this.day19 = day19;
//        this.day20 = day20;
//        this.day21 = day21;
//        this.day22 = day22;
//        this.day23 = day23;
//        this.day24 = day24;
//        this.day25 = day25;
//        this.day26 = day26;
//        this.day27 = day27;
//        this.day28 = day28;
//        this.day29 = day29;
//        this.day30 = day30;
//        this.day31 = day31;
//    }
//
//}
