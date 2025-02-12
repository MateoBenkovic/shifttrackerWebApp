package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.CompanyDTO;
import com.hita.shifttracker.model.Company;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {
    Company findById(int id);

    @Query("SELECT c.company_id, c.company_name, c.company_address, c.postal_code, c.company_city, c.company_tax_id, c.company_iban " +
            "FROM company c WHERE c.company_id = :id")
    CompanyDTO findByIdWithData(int id);
}
