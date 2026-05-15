package com.ticketpro.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketpro.backend.model.Categoria;
import com.ticketpro.backend.model.EstadoEvento;
import com.ticketpro.backend.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Obtener eventos a partir del identificador de proveedor
    
    public List<Evento> findByProveedorId(Long id);

    // Obtener eventos a partir de un estado determinado

    public List<Evento> findByEstado(EstadoEvento estado);

    // Método para filtrar por categoría 

    public List<Evento> findByCategoriaNombre(String nombre);

    public List<Evento> findByUbicacionNombre(String nombre);

} // interface