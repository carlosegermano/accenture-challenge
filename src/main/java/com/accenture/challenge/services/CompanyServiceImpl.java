package com.accenture.challenge.services;

import com.accenture.challenge.model.Address;
import com.accenture.challenge.model.Company;
import com.accenture.challenge.model.CompanyCreationDTO;
import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final SupplierService supplierService;
    private final ZipCodeService zipCodeService;

    private static final String STATE = "PR";

    @Override
    public Company save(CompanyCreationDTO company) {
        Address address = this.zipCodeService.getAddressByZipCode(company.getZipCode());
        company.setAddress(address);

        if (company.getAddress().getUf().equals(STATE)) {
            for (Supplier supplier : company.getSuppliers()) {
                if (!ObjectUtils.isEmpty(supplier.getBirthday()) && isUnderage(supplier)) {
                    throw new IllegalArgumentException("Underage supplier is not allowed in this state!");
                }
            }
        }

        return this.companyRepository.save(
                Company.builder()
                        .cnpj(company.getCnpj())
                        .tradeName(company.getTradeName())
                        .zipCode(company.getZipCode())
                        .address(company.getAddress())
                        .suppliers(company.getSuppliers())
                        .build()
        );
    }

    private boolean isUnderage(Supplier obj) {
        return Period.between(obj.getBirthday(), LocalDate.now()).getYears() < 18;
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
