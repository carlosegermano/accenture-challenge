package com.accenture.challenge.model;

import com.accenture.challenge.model.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpfOrCnpj;
    private String name;
    private String email;
    private String zipCode;
    private String nationalId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;
    @Builder.Default
    @JsonIgnore
    @ManyToMany(mappedBy="suppliers")
    private List<Company> companies = new ArrayList<>();
}
