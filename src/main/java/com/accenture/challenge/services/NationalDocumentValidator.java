package com.accenture.challenge.services;

import com.accenture.challenge.enums.Person;
import com.accenture.challenge.exceptions.FieldMessage;
import com.accenture.challenge.model.SupplierCreationDTO;
import com.accenture.challenge.utils.NationalDocument;
import com.accenture.challenge.validations.NationalDocumentValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class NationalDocumentValidator implements ConstraintValidator<NationalDocumentValidation, SupplierCreationDTO> {

    @Override
    public void initialize(NationalDocumentValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(SupplierCreationDTO supplierCreationDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        if (supplierCreationDTO.getPersonType().equals(Person.NATURAL_PERSON) &&
            !NationalDocument.isValidCPF(supplierCreationDTO.getNationalDocument())) {
            list.add(new FieldMessage("nationalDocument", "CPF inválido!"));
        }

        if (supplierCreationDTO.getPersonType().equals(Person.LEGAL_PERSON) &&
                !NationalDocument.isValidCNPJ(supplierCreationDTO.getNationalDocument())) {
            list.add(new FieldMessage("nationalDocument", "CNPJ inválido!"));
        }

        for (FieldMessage fieldMessage : list) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
