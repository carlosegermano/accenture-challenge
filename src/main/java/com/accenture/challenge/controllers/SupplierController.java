package com.accenture.challenge.controllers;

import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public Supplier saveSupplier(@Valid @RequestBody Supplier supplier) throws Exception {
        return this.supplierService.save(supplier);
    }

    @GetMapping(value = "/{supplierId}")
    public Supplier findById(@PathVariable Long supplierId) {
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
    public Supplier update(@RequestBody Supplier supplier, @PathVariable Long supplierId) {
        return this.supplierService.update(supplier, supplierId);
    }

    @DeleteMapping(value = "/{supplierId}")
    public void delete(@PathVariable Long supplierId) {
        this.supplierService.delete(supplierId);
    }
}
