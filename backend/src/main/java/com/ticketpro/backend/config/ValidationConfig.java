package com.ticketpro.backend.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ticketpro.backend.model.Categoria;
import com.ticketpro.backend.model.Evento;
import com.ticketpro.backend.model.Proveedor;
import com.ticketpro.backend.model.Ubicacion;

@Component
public class ValidationConfig {

    public boolean isNullOrEqualsZero(Long id) {
        return id == null || id == 0;

    }

    public boolean eventsListIsEmpty(List<Evento> eventos) {
        return eventos.isEmpty();

    }

    public boolean identifiersListIsEmpty(List<Long> eventosIds) {
        return eventosIds.isEmpty();

    }

    public boolean isCategoriaNullOrNot(Categoria categoria) {
        return categoria == null;
    }

    public boolean isUbicacionNullOrNot(Ubicacion ubicacion) {
        return ubicacion == null;
    }

    public boolean isProveedorNullOrNot(Proveedor proveedor) {
        return proveedor == null;
    }

} // class