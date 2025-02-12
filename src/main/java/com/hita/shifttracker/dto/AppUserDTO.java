package com.hita.shifttracker.dto;

import lombok.Data;

@Data
public class AppUserDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // role
    private String roleName;
    private String roleUserTypeCode;

    // tim
    private String teamName;

    // org unit
    private String orgUnitName;
}
