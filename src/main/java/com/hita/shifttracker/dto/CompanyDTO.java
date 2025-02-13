package com.hita.shifttracker.dto;

import lombok.Data;

@Data
public class CompanyDTO {

    private int companyId;

    private String companyName;
    private String companyAddress;
    private String postalCode;
    private String companyCity;

    private String companyTaxId;
    private String companyIBAN;

}
