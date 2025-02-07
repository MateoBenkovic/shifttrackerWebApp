package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_role")
@Data
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String roleName;

    @Column(name = "order_number")
    private int roleOrderNumber;

    @Column(name = "user_type_code", length = 1)
    private String roleUserTypeCode;
}
