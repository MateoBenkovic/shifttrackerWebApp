package com.hita.shifttracker.model;

import jakarta.persistence.*;

@Entity
public class TeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int orderNumber;
    private String name;

}
