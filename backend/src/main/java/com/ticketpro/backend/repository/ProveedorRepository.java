package com.ticketpro.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketpro.backend.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Método para encontrar a un proveedor a partir de email:

    public Optional<Proveedor> findByEmail(String email);

    // Método para comprobar si existe proveedor por email 

    public boolean existsByEmail(String email);

} // class