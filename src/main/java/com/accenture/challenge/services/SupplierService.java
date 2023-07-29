package com.accenture.challenge.services;

import com.accenture.challenge.model.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier save(Supplier supplier);
    Supplier findById(Long id);
    List<Supplier> findAll();
    Supplier update(Supplier supplier, Long supplierId);
    void delete(Long supplierId);
}
