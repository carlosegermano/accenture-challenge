package com.accenture.challenge.services;

import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.model.SupplierCreationDTO;
import com.accenture.challenge.model.SupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    SupplierDTO save(SupplierCreationDTO supplier) throws Exception;
    SupplierDTO findById(Long id);
    List<Supplier> findAll();
    Page<Supplier> findAllByNameOrNationalDocumentContaining(Pageable pageable, Optional<String> name, Optional<String> nationalDocument);
    Supplier update(SupplierCreationDTO supplier, Long supplierId);
    void delete(Long supplierId);
}
