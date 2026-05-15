package com.ticketpro.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketpro.backend.dto.UbicacionDTO;
import com.ticketpro.backend.service.UbicacionService;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionRestController {

    // Inyección de dependencias

    private final UbicacionService ubicacionService;
    public UbicacionRestController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    // Método para crear una ubicación 

    @PostMapping("/crear")
    public ResponseEntity<UbicacionDTO> crearNuevaUbicacion(@RequestBody UbicacionDTO ubicacionDTO) {
        UbicacionDTO ubicacion = ubicacionService.crearNuevaUbicacion(ubicacionDTO);
        return ResponseEntity
            .status(200)
            .body(ubicacion);

    } // crearNuevaUbicacion

    // Obtener ubicación por nombre 

    @GetMapping("/nombre")
    public ResponseEntity<UbicacionDTO> obtenerUbicacionNombre(@RequestParam String nombre) {
        UbicacionDTO ubicacion = ubicacionService.obtenerUbicacionPorNombre(nombre);
        return ResponseEntity
            .status(200)
            .body(ubicacion);

    }

    // Obtener todas las ubicaciones 

    @GetMapping
    public ResponseEntity<List<UbicacionDTO>> obtenerTodasLasUbicaciones() {
        List<UbicacionDTO> ubicaciones = ubicacionService.obtenerTodasLasUbicaciones();
        return ResponseEntity.ok(ubicaciones);

    }

    // Obtener una ubicación por id 

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> obtenerUbicacionPorId(@PathVariable Long id) {
        UbicacionDTO ubicacion = ubicacionService.obtenerUbicacionPorId(id);
        return ResponseEntity
            .status(200)
            .body(ubicacion);

    }

    // Método para eliminar una ubicación 

    @DeleteMapping("/borrar")
    public ResponseEntity<Void> eliminarUbicacion(@RequestBody UbicacionDTO ubicacionDTO) {
        ubicacionService.eliminarUbicacion(ubicacionDTO);
        return ResponseEntity.noContent().build();

    }


} // class