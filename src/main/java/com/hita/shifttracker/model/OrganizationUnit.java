package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "org_unit")
@Data
public class OrganizationUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int organizationUnitId;

    @Column(name = "name")
    private String organizationUnitName;

    @Column(name = "order_number")
    private int organizationUnitOrderNumber;

//    private String organizationUnitAddress;
//    private String organizationUnitCity;

    //private AppUser appUser;

    //private City cityId;
}
