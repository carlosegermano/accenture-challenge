package com.accenture.challenge.controllers;

import com.accenture.challenge.model.Company;
import com.accenture.challenge.model.CompanyCreationDTO;
import com.accenture.challenge.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public Company save(@RequestBody CompanyCreationDTO company) {
        return this.companyService.save(company);
    }

    @GetMapping(value = "/{companyId}")
    public Company findById(@PathVariable Long companyId) {
        return this.companyService.findById(companyId);
    }

    @GetMapping
    public List<Company> findAll() {
        return this.companyService.findAll();
    }

    @PutMapping(value = "/{companyId}")
    public Company update(@RequestBody Company company, @PathVariable Long companyId) {
        return this.companyService.update(company, companyId);
    }

    @DeleteMapping(value = "/{companyId}")
    public void delete(@PathVariable Long companyId) {
        this.companyService.delete(companyId);
    }
}
