package com.accenture.challenge.services;

import com.accenture.challenge.repositories.SupplierRepository;
import com.accenture.challenge.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier save(Supplier supplier) {
        return this.supplierRepository.save(supplier);
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
    public Supplier update(Supplier supplier, Long supplierId) {
        Supplier supp = findById(supplierId);
        supp.setCpfOrCnpj(supplier.getCpfOrCnpj());
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
