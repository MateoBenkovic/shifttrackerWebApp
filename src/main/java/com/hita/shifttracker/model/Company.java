package com.hita.shifttracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "company")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private int companyId;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "postal_code", nullable = false, length = 5)
    private String postalCode;

    @Column(name = "company_address", nullable = false, length = 50)
    private String companyAddress;

    @Column(name = "company_bank", nullable = false, length = 50)
    private String companyBank;

    @Column(name = "company_tax_id", nullable = false, length = 11)
    private String companyTaxId;

    @Column(name = "company_iban", nullable = false, length = 21)
    private String companyIBAN;

    @Column(name = "company_email", nullable = false, length = 50)
    private String companyEmail;

    @Column(name = "company_phone", nullable = false, length = 30)
    private String companyPhone;

    @Column(name = "company_fax", nullable = false, length = 30)
    private String companyFax;

    @Column(name = "base_salary_month", precision = 10, scale = 2)
    private BigDecimal baseSalaryMonth;

    @Column(name = "base_salary_hour", precision = 8, scale = 4)
    private BigDecimal baseSalaryHour;
}
