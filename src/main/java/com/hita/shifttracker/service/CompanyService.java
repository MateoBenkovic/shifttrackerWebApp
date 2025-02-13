package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.CompanyDTO;
import com.hita.shifttracker.model.Company;
import com.hita.shifttracker.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyDTO findByIdWithData(int id) {
        return companyRepository.findByIdWithData(id);
    }
}
