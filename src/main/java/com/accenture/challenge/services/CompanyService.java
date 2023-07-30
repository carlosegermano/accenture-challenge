package com.accenture.challenge.services;

import com.accenture.challenge.model.Company;
import com.accenture.challenge.model.CompanyCreationDTO;

import java.util.List;

public interface CompanyService {

    Company save(CompanyCreationDTO company);
    Company findById(Long id);
    List<Company> findAll();
    Company update(Company company, Long companyId);
    void delete(Long companyId);
}
