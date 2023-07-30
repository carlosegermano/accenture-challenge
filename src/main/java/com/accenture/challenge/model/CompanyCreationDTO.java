package com.accenture.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String cnpj;
    private String tradeName;
    private String zipCode;

    private Address address;

    @Builder.Default
    private List<Supplier> suppliers = new ArrayList<>();
}
