package com.accenture.challenge.services;

import com.accenture.challenge.enums.Person;
import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final NationalDocumentValidator nationalDocumentValidator;

    @Override
    public Supplier save(Supplier supplier) throws Exception {

        if (!this.nationalDocumentValidator.isZipCodeValid(supplier.getZipCode())) {
            throw new IllegalArgumentException("ZipCode is not valid!");
        }

        this.setPersonType(supplier);
        return this.supplierRepository.save(supplier);
    }

    private void setPersonType(Supplier supplier) {
        if (supplier.getNationalDocument().length() == 11) {
            supplier.setPersonType(Person.NATURAL_PERSON);
        } else {
            supplier.setPersonType(Person.LEGAL_PERSON);
        }
    }

    @Override
    public Supplier findById(Long id) {
        return this.supplierRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Supplier.class, String.valueOf(id)));
    }

    @Override
    public List<Supplier> findAll() {
        return this.supplierRepository.findAll();
    }

    @Override
    public Page<Supplier> findAllByNameOrNationalDocumentContaining(Pageable pageable, Optional<String> name, Optional<String> nationalDocument) {
        return this.supplierRepository.findAllByNameOrNationalDocumentContaining(pageable, name, nationalDocument);
    }

    @Override
    public Supplier update(Supplier supplier, Long supplierId) {
        Supplier supp = findById(supplierId);
        supp.setNationalDocument(supplier.getNationalDocument());

        supp.setName(supplier.getName());
        supp.setEmail(supplier.getEmail());
        supp.setZipCode(supplier.getZipCode());
        return this.supplierRepository.save(supp);
    }

    @Override
    public void delete(Long supplierId) {
        this.supplierRepository.deleteById(supplierId);
    }
}
