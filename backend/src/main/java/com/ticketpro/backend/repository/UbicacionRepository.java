package com.ticketpro.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketpro.backend.model.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {

    // Comprobar si existe ubicación por nombre 

    public boolean existsByNombre(String nombre);

    // Obtener una ubicación por nombre 

    public Optional<Ubicacion> findByNombre(String nombre);

} // class