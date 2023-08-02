package com.accenture.challenge.builders;

import com.accenture.challenge.model.CompanyCreationDTO;

import java.util.Arrays;

public class CompanyCreationDTOBuilder {

    public static CompanyCreationDTO createCompanyCreationDTO() {
        return CompanyCreationDTO.builder()
                .cnpj("32069325000117")
                .tradeName("Teste")
                .zipCode("58297000")
                .address(AddressBuilder.createAddress())
                .suppliers(Arrays.asList(SupplierBuilder.createNaturalPersonSupplier()))
                .build();
    }

    public static CompanyCreationDTO createCompanyCreationDTOWithUnderageSupplier() {
        return CompanyCreationDTO.builder()
                .cnpj("32069325000117")
                .tradeName("Teste")
                .zipCode("58297000")
                .address(AddressBuilder.createAddress())
                .suppliers(Arrays.asList(SupplierBuilder.createUnderageSupplier()))
                .build();
    }
}
