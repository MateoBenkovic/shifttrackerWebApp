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
//
//    @Column(name = "user_code", nullable = false, length = 5)
//    private String appUserCode;

    @ManyToOne
    @JoinColumn(name = "org_unit_id")
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


    public AppUser(String firstName, String lastName, String oib, String telephone, OrganizationUnit organizationUnit, AppRole appRole, TeamRole teamRole, Team team, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.telephone = telephone;
        this.organizationUnit = organizationUnit;
        this.appRole = appRole;
        this.teamRole = teamRole;
        this.team = team;
        this.email = email;
        this.password = password;
    }

    public AppUser() {
    }
}
