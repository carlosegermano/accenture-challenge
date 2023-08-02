package com.accenture.challenge.builders;

import com.accenture.challenge.model.Company;

import java.util.Arrays;
import java.util.List;

public class CompanyBuilder {

    public static Company createCompany() {
        return Company.builder()
                .id(1L)
                .cnpj("32069325000117")
                .tradeName("Teste")
                .zipCode("58297000")
                .address(AddressBuilder.createAddress())
                .suppliers(Arrays.asList(SupplierBuilder.createNaturalPersonSupplier()))
                .build();
    }

    public static Company createCompany2() {
        return Company.builder()
                .id(2L)
                .cnpj("32069325000117")
                .tradeName("Teste")
                .zipCode("58297000")
                .address(AddressBuilder.createAddress())
                .suppliers(Arrays.asList(SupplierBuilder.createNaturalPersonSupplier()))
                .build();
    }

    public static List<Company> createListOfCompany() {
        return Arrays.asList(createCompany(), createCompany2());
    }
}
