package com.accenture.challenge.model;

import com.accenture.challenge.enums.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nationalDocument;
    private Person personType;
    private String name;
    private String email;
    private String zipCode;
    private String nationalId;
    private String birthday;
    private List<Company> companies;
}
