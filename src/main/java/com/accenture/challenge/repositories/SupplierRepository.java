package com.accenture.challenge.repositories;

import com.accenture.challenge.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Page<Supplier> findAllByNameOrNationalDocumentContaining(
            Pageable pageable,
            Optional<String> name,
            Optional<String> nationalDocument
    );
}
