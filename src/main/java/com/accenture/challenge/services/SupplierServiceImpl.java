package com.accenture.challenge.services;

import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.model.SupplierCreationDTO;
import com.accenture.challenge.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ZipCodeService zipCodeService;

    @Override
    public Supplier save(SupplierCreationDTO supplier) throws Exception {

        if (!this.zipCodeService.isZipCodeValid(supplier.getZipCode())) {
            throw new IllegalArgumentException("ZipCode is not valid!");
        }

        return this.supplierRepository.save(
                Supplier.builder()
                        .nationalDocument(supplier.getNationalDocument())
                        .personType(supplier.getPersonType())
                        .name(supplier.getName())
                        .email(supplier.getEmail())
                        .zipCode(supplier.getZipCode())
                        .nationalId(supplier.getNationalId())
                        .birthday(new Date(supplier.getBirthday()))
                        .build()
        );
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
