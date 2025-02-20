package com.hita.shifttracker.dto;

import lombok.Data;

@Data
public class AppUserDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userCode;

    // role
    private String roleName;
    private String roleUserTypeCode;

    // tim
    private String teamName;

    // org unit
    private String orgUnitName;

    // team role
    private String teamRoleName;
}
