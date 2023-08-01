package com.accenture.challenge.model;

import com.accenture.challenge.enums.Person;
import com.accenture.challenge.validations.ConditionalValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConditionalValidation(
        conditionalProperty = "personType", values = {"NATURAL_PERSON"},
        requiredProperties = {"nationalId", "birthday"},
        message = "Identidade e data de nascimento é obrigatório para pessoa física!")
public class SupplierCreationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @Pattern(regexp = "^[0-9]{11,14}$", message = "Only digits are accepted")
    private String nationalDocument;
    private Person personType;
    private String name;
    private String email;
    private String zipCode;
    private String nationalId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String birthday;
    @Builder.Default
    private List<Company> companies = new ArrayList<>();
}
