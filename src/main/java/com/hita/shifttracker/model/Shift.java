//package com.hita.shifttracker.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "shift")
//@Data
//public class Shift {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(name = "name", length = 50, nullable = false)
//    private String name;
//    @Column(name = "shift_id", nullable = false, unique = true)
//    private int shiftId;
//    @Column(name = "start_time", nullable = false)
//    private int startTime;
//    @Column(name = "end_time", nullable = false)
//    private int endTime;
//
//}
