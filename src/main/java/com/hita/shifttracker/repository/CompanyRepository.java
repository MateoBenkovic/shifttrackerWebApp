package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.Company;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompanyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Company findByIdWithData(int id) {
        String sql = "SELECT c.company_id, c.company_name, c.company_address, " +
                "c.postal_code, c.company_city, c.company_tax_id, c.company_iban " +
                "FROM company c WHERE c.company_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Company company = new Company();

            company.setCompanyId(rs.getInt("company_id"));
            company.setCompanyName(rs.getString("company_name"));
            company.setCompanyAddress(rs.getString("company_address"));
            company.setPostalCode(rs.getString("postal_code"));
            company.setCompanyCity(rs.getString("company_city"));
            company.setCompanyTaxId(rs.getString("company_tax_id"));
            company.setCompanyIBAN(rs.getString("company_iban"));

            return company;
        });
    }
}
