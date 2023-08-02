package com.accenture.challenge.services;

import com.accenture.challenge.exceptions.DataIntegrityValidationException;
import com.accenture.challenge.exceptions.ObjectNotFoundException;
import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.model.SupplierCreationDTO;
import com.accenture.challenge.model.SupplierDTO;
import com.accenture.challenge.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
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
    public SupplierDTO save(SupplierCreationDTO supplier) {

        this.zipCodeService.getAddressByZipCode(supplier.getZipCode());

        try {
            Supplier saved = this.supplierRepository.save(
                    Supplier.builder()
                            .nationalDocument(supplier.getNationalDocument())
                            .personType(supplier.getPersonType())
                            .name(supplier.getName())
                            .email(supplier.getEmail())
                            .zipCode(supplier.getZipCode())
                            .nationalId(supplier.getNationalId())
                            .birthday(
                                    !ObjectUtils.isEmpty(supplier.getBirthday())
                                            ? parseBirthday(supplier.getBirthday())
                                            : null
                            )
                            .build()
            );

            return SupplierDTO.builder()
                    .id(saved.getId())
                    .nationalDocument(saved.getNationalDocument())
                    .personType(saved.getPersonType())
                    .name(saved.getName())
                    .email(saved.getEmail())
                    .zipCode(saved.getZipCode())
                    .nationalId(saved.getNationalId())
                    .birthday(String.valueOf(saved.getBirthday()))
                    .companies(saved.getCompanies())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityValidationException("Campo já existente!");
        }
    }

    @Override
    public SupplierDTO findById(Long id) {
         Supplier supplier = this.supplierRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado!"));

         return SupplierDTO.builder()
                .id(supplier.getId())
                .nationalDocument(supplier.getNationalDocument())
                .personType(supplier.getPersonType())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .zipCode(supplier.getZipCode())
                .nationalId(supplier.getNationalId())
                .birthday(String.valueOf(supplier.getBirthday()))
                .companies(supplier.getCompanies())
                .build();
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
    public Supplier update(SupplierCreationDTO supplier, Long supplierId) {

        this.zipCodeService.getAddressByZipCode(supplier.getZipCode());

        Supplier supplierToSave = Supplier.builder()
                .id(supplier.getId())
                .nationalDocument(supplier.getNationalDocument())
                .personType(supplier.getPersonType())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .zipCode(supplier.getZipCode())
                .nationalId(supplier.getNationalId())
                .birthday(
                        !ObjectUtils.isEmpty(supplier.getBirthday())
                                ? parseBirthday(supplier.getBirthday().toString())
                                : null
                )
                .companies(supplier.getCompanies())
                .build();

        try {
            return this.supplierRepository.save(supplierToSave);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityValidationException("Campo já existente!");
        }
    }

    @Override
    public void delete(Long supplierId) {
        this.findById(supplierId);
        this.supplierRepository.deleteById(supplierId);
    }

    @NotNull
    private static LocalDate parseBirthday(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        OffsetDateTime dateTime = OffsetDateTime.parse(date);
        return LocalDate.parse(dateTime.format(dtf));
    }
}
