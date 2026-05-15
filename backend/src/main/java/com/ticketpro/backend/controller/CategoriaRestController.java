package com.ticketpro.backend.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketpro.backend.dto.CategoriaDTO;
import com.ticketpro.backend.exception.CategoriaAlreadyExistsException;
import com.ticketpro.backend.exception.CategoriaNotFoundException;
import com.ticketpro.backend.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {

    private final CategoriaService categoriaService;
    public CategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Método para crear una nueva categoría

    @PostMapping("/crear")
    public ResponseEntity<CategoriaDTO> crearNuevaCategoria(@RequestBody CategoriaDTO categoriaDTO) throws CategoriaAlreadyExistsException {
        CategoriaDTO categoriaCreada = categoriaService.crearNuevaCategoria(categoriaDTO);
        return ResponseEntity
            .status(200)
            .body(categoriaCreada);

    } // crearNuevaCategoria

    // Método para obtener todas las categorías 

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodasLasCategorias() {
        List<CategoriaDTO> categoriaDTOs = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity
            .ok(categoriaDTOs);

    } // obtenerTodasLasCategorias

    // Método para obtener una categoría por id 

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Long id) throws CategoriaNotFoundException {
        CategoriaDTO categoriaDTO = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity
            .status(200)
            .body(categoriaDTO);

    } // obtenerCategoriaPorId

    // Método para eliminar una categoría

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarCategoria(@RequestBody CategoriaDTO categoriaDTO) throws CategoriaNotFoundException {
        categoriaService.eliminarCategoria(categoriaDTO);
        return ResponseEntity.noContent().build();

    }

} // class