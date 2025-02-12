package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "org_unit")
@Data
public class OrganizationUnit {

    @Id
    @Column("id")
    private int organizationUnitId;

    @Column("name")
    private String organizationUnitName;

    @Column("order_number")
    private int organizationUnitOrderNumber;

    @Column("address")
    private String organizationUnitAddress;

    @Column("city")
    private String organizationUnitCity;

    // app user id

    // city id
}
