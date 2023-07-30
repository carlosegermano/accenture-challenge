package com.accenture.challenge.services;

import com.accenture.challenge.model.Company;
import com.accenture.challenge.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final NationalDocumentValidator nationalDocumentValidator;

    @Override
    public Company save(Company company) {
        if (!this.nationalDocumentValidator.isZipCodeValid(company.getZipCode())) {
            throw new IllegalArgumentException("ZipCode is not valid!");
        }
        return this.companyRepository.save(company);
    }

    @Override
    public Company findById(Long id) {
        return this.companyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Company.class, String.valueOf(id)));
    }

    @Override
    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    @Override
    public Company update(Company company, Long companyId) {
        Company companySaved = this.findById(companyId);
        companySaved.setCnpj(company.getCnpj());
        companySaved.setTradeName(company.getTradeName());
        companySaved.setZipCode(company.getZipCode());
        return this.companyRepository.save(companySaved);
    }

    @Override
    public void delete(Long companyId) {
        this.companyRepository.deleteById(companyId);
    }
}
