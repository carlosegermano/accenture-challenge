package com.accenture.challenge.controllers;

import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.model.SupplierCreationDTO;
import com.accenture.challenge.model.SupplierDTO;
import com.accenture.challenge.services.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public SupplierDTO saveSupplier(@Valid @RequestBody SupplierCreationDTO supplier) throws Exception {
        return this.supplierService.save(supplier);
    }

    @GetMapping(value = "/{supplierId}")
    public SupplierDTO findById(@PathVariable Long supplierId) {
        return this.supplierService.findById(supplierId);
    }

    @GetMapping
    public List<Supplier> findAll() {
        return this.supplierService.findAll();
    }

    @GetMapping(value = "/search")
    public Page<Supplier> findAllByNameOrNationalDocumentContaining(
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "nationalDocument", required = false) Optional<String> nationalDocument,
            Pageable pageable) {
        return this.supplierService.findAllByNameOrNationalDocumentContaining(pageable, name, nationalDocument);
    }

    @PutMapping(value = "/{supplierId}")
    public Supplier update(@RequestBody SupplierCreationDTO supplier, @PathVariable Long supplierId) {
        return this.supplierService.update(supplier, supplierId);
    }

    @DeleteMapping(value = "/{supplierId}")
    public void delete(@PathVariable Long supplierId) {
        this.supplierService.delete(supplierId);
    }
}
