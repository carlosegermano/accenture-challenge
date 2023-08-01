package com.accenture.challenge.services;

import com.accenture.challenge.exceptions.ObjectNotFoundException;
import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.model.SupplierCreationDTO;
import com.accenture.challenge.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ZipCodeService zipCodeService;

    @Override
    public Supplier save(SupplierCreationDTO supplier) {

        this.zipCodeService.getAddressByZipCode(supplier.getZipCode());

        return this.supplierRepository.save(
                Supplier.builder()
                        .nationalDocument(supplier.getNationalDocument())
                        .personType(supplier.getPersonType())
                        .name(supplier.getName())
                        .email(supplier.getEmail())
                        .zipCode(supplier.getZipCode())
                        .nationalId(supplier.getNationalId())
                        .birthday(!ObjectUtils.isEmpty(supplier.getBirthday()) ? getBirthday(supplier) : null)
                        .build()
        );
    }

    @NotNull
    private static LocalDate getBirthday(SupplierCreationDTO supplier) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        OffsetDateTime dateTime = OffsetDateTime.parse(supplier.getBirthday());
        return LocalDate.parse(dateTime.format(dtf));
    }

    @Override
    public Supplier findById(Long id) {
        return this.supplierRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado!"));
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

        this.zipCodeService.getAddressByZipCode(supplier.getZipCode());

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
