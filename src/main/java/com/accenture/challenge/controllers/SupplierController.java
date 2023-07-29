package com.accenture.challenge.controllers;

import com.accenture.challenge.model.Company;
import com.accenture.challenge.services.SupplierService;
import com.accenture.challenge.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public Supplier saveSupplier(@RequestBody Supplier supplier) {
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

    @PutMapping(value = "/{supplierId}")
    public Supplier update(@RequestBody Supplier supplier, @PathVariable Long supplierId) {
        return this.supplierService.update(supplier, supplierId);
    }

    @DeleteMapping(value = "/{supplierId}")
    public void delete(@PathVariable Long supplierId) {
        this.supplierService.delete(supplierId);
    }
}
