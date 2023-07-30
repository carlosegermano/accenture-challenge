package com.accenture.challenge.services;

import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.model.SupplierCreationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    Supplier save(SupplierCreationDTO supplier) throws Exception;
    Supplier findById(Long id);
    List<Supplier> findAll();
    Page<Supplier> findAllByNameOrNationalDocumentContaining(Pageable pageable, Optional<String> name, Optional<String> nationalDocument);
    Supplier update(Supplier supplier, Long supplierId);
    void delete(Long supplierId);
}
