package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // find by id
}
