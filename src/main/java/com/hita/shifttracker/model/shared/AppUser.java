package com.hita.shifttracker.model.shared;

import jakarta.persistence.*;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String oib;

    @ManyToOne
    @JoinColumn(name = "org_unit_id")
    private OrgUnit orgUnit;

    private String email;
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "app_role_id")
    private  AppRole appRole;

    @ManyToOne
    @JoinColumn(name = "team_role_id")
    private TeamRole teamRole;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "schedule_team_per_shift_id")
    private ScheduleTeamPerShift scheduleTeamPerShift;

    public AppUser() {
    }

    public AppUser(int id, String firstName, String lastName, String oib, OrgUnit orgUnit, String email,
                   String telephone, AppRole appRole, TeamRole teamRole, Team team,
                   ScheduleTeamPerShift scheduleTeamPerShift) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.orgUnit = orgUnit;
        this.email = email;
        this.telephone = telephone;
        this.appRole = appRole;
        this.teamRole = teamRole;
        this.team = team;
        this.scheduleTeamPerShift = scheduleTeamPerShift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public AppRole getAppRole() {
        return appRole;
    }

    public void setAppRole(AppRole appRole) {
        this.appRole = appRole;
    }

    public TeamRole getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(TeamRole teamRole) {
        this.teamRole = teamRole;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public ScheduleTeamPerShift getScheduleTeamPerShift() {
        return scheduleTeamPerShift;
    }

    public void setScheduleTeamPerShift(ScheduleTeamPerShift scheduleTeamPerShift) {
        this.scheduleTeamPerShift = scheduleTeamPerShift;
    }
}
