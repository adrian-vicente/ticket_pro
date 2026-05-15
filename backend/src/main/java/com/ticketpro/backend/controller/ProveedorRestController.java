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
import com.ticketpro.backend.dto.ProveedorDTO;
import com.ticketpro.backend.service.ProveedorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorRestController {

    // Inyección de dependencias

    private final ProveedorService proveedorService;

    public ProveedorRestController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;

    } // 

    // Método para crear un nuevo proveedor en la base de datos 

    @PostMapping
    public ResponseEntity<ProveedorDTO> crearNuevoProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO prov = proveedorService.crearNuevoProveedor(proveedorDTO);
        return ResponseEntity
            .status(201)
            .body(prov);

    } // crearNuevoProveedor

    // Método para obtener un proveedor a partir del identificador 

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtenerProveedorPorId(@PathVariable Long id) {
        ProveedorDTO proveedorDTO = proveedorService.obtenerProveedorPorId(id);
        return ResponseEntity
            .status(200)
            .body(proveedorDTO);

    } // obtenerProveedorPorId

    // Método para modificar los datos de un proveedor existente 

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> modificarDatosProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO, @PathVariable Long id) throws Exception {
        ProveedorDTO proveedorModificado = proveedorService.modificarDatosProveedor(proveedorDTO, id);
        return ResponseEntity
            .status(200)
            .body(proveedorModificado);

    } // modificarDatosProveedor

    // Método para obtener todos los proveedores registrados 
    
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> obtenerTodosLosProveedores() {
        List<ProveedorDTO> proveedoresDtos = proveedorService.obtenerTodosLosProveedores();
        return ResponseEntity
            .ok(proveedoresDtos);

    } // obtenerTodosLosProveedores

    // Método para activar un proveedor en la aplicación 

    @PutMapping("/{id}/activar")
    public ResponseEntity<ProveedorDTO> activarProveedor(@PathVariable Long id) {
        ProveedorDTO proveedorActivado = proveedorService.activarProveedor(id);
        return ResponseEntity
            .status(200)
            .body(proveedorActivado);

    } // activarProveedor

    // Método para obtener proveedor a partir del email 

    @GetMapping("/obtener")
    public ResponseEntity<ProveedorDTO> obtenerProveedorPorEmail(@RequestParam String email) {
        ProveedorDTO proveedorDTO = proveedorService.obtenerProveedorPorEmail(email);
        return ResponseEntity
            .status(200)
            .body(proveedorDTO);

    } // obtenerProveedorPorEmail

    // Método para eliminar un proveedor

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProveedor(@PathVariable Long id) {
        ProveedorDTO proveedorDTO = proveedorService.desactivarProveedor(id);
        System.out.println("El proveedor: " + proveedorDTO.getNombre() + " se ha desactivado correctamente.");
        return ResponseEntity.noContent().build();

    } // borrarProveedor

} // class