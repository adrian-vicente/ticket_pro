package com.ticketpro.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketpro.backend.dto.EventoDTO;
import com.ticketpro.backend.exception.EventoNotFoundException;
import com.ticketpro.backend.service.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/eventos")
public class EventoRestController {

    // Inyección de dependencias

    private final EventoService eventoService;
    public EventoRestController(EventoService eventoService) {
        this.eventoService = eventoService;

    } 

    // Método para crear un nuevo evento 

    @PostMapping
    public ResponseEntity<EventoDTO> crearNuevoEvento(@RequestBody EventoDTO eventoDTO) throws Exception {
        EventoDTO eventoCreado = eventoService.crearNuevoEvento(eventoDTO);
        return ResponseEntity.ok( eventoCreado );

    } // crearNuevoEvento

    // Método para obtener todos los eventos 

    @GetMapping
    public ResponseEntity<List<EventoDTO>> obtenerTodosLosEventos() throws Exception {
        List<EventoDTO> eventoDTOs = eventoService.obtenerTodosLosEventos();
        return ResponseEntity
            .status(200)
            .body( eventoDTOs );

    } // obtenerTodosLosEventos

    // Método para obtener un evento por identificador

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerEventoPorId(@PathVariable Long id) throws Exception {
        EventoDTO eventoDTO = eventoService.obtenerEventoPorId(id);
        return ResponseEntity
            .status(200)
            .body(eventoDTO);

    } // obtenerEventoPorId

    // Método para modificar los datos de un evento 

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> modificarEventoExistente(@PathVariable Long id, @Valid @RequestBody EventoDTO eventoDTO) throws Exception {
        EventoDTO eventoModificado = eventoService.modificarEventoExistente(eventoDTO, id);
        return ResponseEntity
            .status(200)
            .body(eventoModificado);

    } // modificarEventoExistente

    // Método para eliminar un evento 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedorExistente(@PathVariable Long id) throws EventoNotFoundException {
        eventoService.eliminarEventoExistente(id);
        return ResponseEntity.noContent().build();

    } // eliminarProveedorExistente

    // Método para obtener los eventos a partir del id de un proveedor 

    @GetMapping("/proveedor/{id}")
    public ResponseEntity<List<EventoDTO>> obtenerEventosProveedorId(@PathVariable Long id) throws Exception {
        List<EventoDTO> eventoDTOs = eventoService.obtenerEventosProveedorId(id);
        return ResponseEntity
            .status(201)
            .body(eventoDTOs);
            
    } // obtenerEventosProveedorId

    // Método para obtener los eventos activos únicamente 

    @GetMapping("/activos")
    public ResponseEntity<List<EventoDTO>> obtenerEventosActivos() throws Exception {
        List<EventoDTO> eventoDTOs = eventoService.obtenerEventosActivos();
        return ResponseEntity
            .status(200)
            .body(eventoDTOs);

    }

    // Método para filtrar por categoría 

    @GetMapping("/categoria")
    public ResponseEntity<List<EventoDTO>> obtenerEventosPorCategoria(@RequestParam String nombre) throws Exception {
        List<EventoDTO> eventos = eventoService.obtenerEventosPorCategoria(nombre);
        return ResponseEntity.ok(eventos);

    }

    // Método para filtrar por ubicación 

    // Método para activar un evento 

    // Método para desactivar un evento

} // class