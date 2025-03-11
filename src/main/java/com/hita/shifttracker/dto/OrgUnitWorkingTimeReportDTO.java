package com.hita.shifttracker.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrgUnitWorkingTimeReportDTO {

    private int orgUnitId;
    private String orgUnitName;
    private int month;
    private int year;
    private String vrstaRada;
    private BigDecimal ukupniSati;
    private BigDecimal ukupnoSatiSveVrste;

    public OrgUnitWorkingTimeReportDTO(int orgUnitId, String orgUnitName, int month, int year, String vrstaRada, BigDecimal ukupniSati, BigDecimal ukupnoSatiSveVrste) {
        this.orgUnitId = orgUnitId;
        this.orgUnitName = orgUnitName;
        this.month = month;
        this.year = year;
        this.vrstaRada = vrstaRada;
        this.ukupniSati = ukupniSati;
        this.ukupnoSatiSveVrste = ukupnoSatiSveVrste;
    }

    public OrgUnitWorkingTimeReportDTO(int month, int year, String vrstaRada, BigDecimal ukupniSati, BigDecimal ukupnoSatiSveVrste) {
        this.orgUnitName = "Hitna KZŽ";
        this.month = month;
        this.year = year;
        this.vrstaRada = vrstaRada;
        this.ukupniSati = ukupniSati;
        this.ukupnoSatiSveVrste = ukupnoSatiSveVrste;
    }
}
