package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int orderNumber;
    private String name;

}
