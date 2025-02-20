package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "app_user")
@Data
public class AppUser {

    @Id
    @Column("id")
    private int id;

    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("oib")
    private String oib;
    @Column("telephone")
    private String telephone;

    @Column("user_code")
    private String appUserCode;

    @Column("org_unit_id")
    private int organizationUnitId;
    private OrganizationUnit organizationUnit;

    @Column("app_role_id")
    private int appRoleId;
    private AppRole appRole;

    @Column("team_role_id")
    private int teamRoleId;

    @Column("team_id")
    private int teamId;
    private Team team;

    @Column("work_role_id")
    private int workRoleId;

    @Column("coefficient")
    private BigDecimal coefficient;
}
