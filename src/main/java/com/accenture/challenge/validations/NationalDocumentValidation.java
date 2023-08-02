package com.accenture.challenge.validations;

import com.accenture.challenge.services.NationalDocumentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NationalDocumentValidator.class})
public @interface NationalDocumentValidation {

    String message() default "Erro de validação.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
