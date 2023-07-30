package com.accenture.challenge.model;

import com.accenture.challenge.enums.Person;
import com.accenture.challenge.validations.ConditionalValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
@ConditionalValidation(
        conditionalProperty = "personType", values = {"NATURAL_PERSON"},
        requiredProperties = {"naturalId", "birthday"},
        message = "Natural ID and Birthday are required for Natural Person.")
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nationalDocument;
    private Person personType;
    private String name;
    private String email;
    private String zipCode;
    private String nationalId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    @JsonIgnore
    @Builder.Default
    @ManyToMany(mappedBy="suppliers")
    @JsonManagedReference
    private List<Company> companies = new ArrayList<>();
}
