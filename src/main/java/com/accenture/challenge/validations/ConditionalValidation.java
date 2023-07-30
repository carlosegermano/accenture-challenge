package com.accenture.challenge.validations;

import com.accenture.challenge.services.ConditionalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(ConditionalValidations.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConditionalValidator.class})
public @interface ConditionalValidation {

    String message() default "Field is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String conditionalProperty();

    String[] values();

    String[] requiredProperties();
}
