package com.ticketpro.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketpro.backend.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Método para saber si existe categoría por nombre 

    public boolean existsByNombre(String nombre);

    // Método para obtener una categoría por nombre 

    public Optional<Categoria> findByNombre(String nombre);

} // interface