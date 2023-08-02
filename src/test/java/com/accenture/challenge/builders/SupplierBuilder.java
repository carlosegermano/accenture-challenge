package com.accenture.challenge.builders;

import com.accenture.challenge.enums.Person;
import com.accenture.challenge.model.Supplier;

import java.time.LocalDate;

public class SupplierBuilder {

    public static Supplier createNaturalPersonSupplier() {
        return Supplier.builder()
                .nationalDocument("21139987003")
                .personType(Person.NATURAL_PERSON)
                .name("John Doe")
                .email("johndoe@gmail.com")
                .zipCode("58292000")
                .nationalId("2456254")
                .birthday(LocalDate.of(2000, 10, 10))
                .build();
    }

    public static Supplier createUnderageSupplier() {
        return Supplier.builder()
                .nationalDocument("21139987003")
                .personType(Person.NATURAL_PERSON)
                .name("John Doe")
                .email("johndoe@gmail.com")
                .zipCode("58292000")
                .nationalId("2456254")
                .birthday(LocalDate.of(2010, 10, 10))
                .build();
    }
}
