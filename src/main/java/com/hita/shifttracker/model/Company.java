package com.hita.shifttracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "company")
@Data
public class Company {

    @Id
    @Column("company_id")
    private int companyId;

    @Column("company_name")
    private String companyName;

    @Column("postal_code")
    private String postalCode;

    @Column("company_address")
    private String companyAddress;

    @Column("company_bank")
    private String companyBank;

    @Column("company_tax_id")
    private String companyTaxId;

    @Column("company_iban")
    private String companyIBAN;

    @Column("company_email")
    private String companyEmail;

    @Column("company_phone")
    private String companyPhone;

    @Column("company_fax")
    private String companyFax;

    @Column("base_salary_month")
    private BigDecimal baseSalaryMonth;

    @Column("base_salary_hour")
    private BigDecimal baseSalaryHour;

    @Column("company_city")
    private String companyCity;
}
