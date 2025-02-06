package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_user")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private String oib;
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "organization_unit_id")
    private OrganizationUnit organizationUnit;

    @ManyToOne
    @JoinColumn(name = "app_role_id")
    private AppRole appRole;

    @ManyToOne
    @JoinColumn(name = "team_role_id")
    private TeamRole teamRole;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    //private String userCode;

}
