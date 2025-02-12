package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "app_role")
@Data
public class AppRole {

    @Id
    @Column("id")
    private int id;

    @Column("name")
    private String roleName;

    @Column("order_number")
    private int roleOrderNumber;

    @Column("user_type_code")
    private String roleUserTypeCode;
}
