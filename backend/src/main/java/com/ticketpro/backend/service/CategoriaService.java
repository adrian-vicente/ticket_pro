package com.ticketpro.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ticketpro.backend.config.ValidationConfig;
import com.ticketpro.backend.dto.CategoriaDTO;
import com.ticketpro.backend.exception.CategoriaAlreadyExistsException;
import com.ticketpro.backend.exception.CategoriaNotFoundException;
import com.ticketpro.backend.model.Categoria;
import com.ticketpro.backend.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ValidationConfig validationConfig;

    public CategoriaService(CategoriaRepository categoriaRepository, ValidationConfig validationConfig) {
        this.categoriaRepository = categoriaRepository;
        this.validationConfig = validationConfig;
        
    }

    public Categoria toEntity(CategoriaDTO categoriaDTO) {

        Categoria categoria = new Categoria();

        if( !validationConfig.isNullOrEqualsZero(categoriaDTO.getId()) ) {
            categoria.setId(categoriaDTO.getId());

        } // if

        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        return categoria;

    } // toEntity

    public CategoriaDTO toDTO(Categoria categoria) {

        CategoriaDTO categoriaDTO = new CategoriaDTO();

        if( !validationConfig.isNullOrEqualsZero(categoria.getId()) ) {
            categoriaDTO.setId(categoria.getId());

        } // if

        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());

        return categoriaDTO;

    } // toDTO

    // Método para crear una nueva categoría

    public CategoriaDTO crearNuevaCategoria(CategoriaDTO categoria) throws CategoriaAlreadyExistsException {
        if( categoriaExistePorNombre(categoria.getNombre()) ) {
            throw new CategoriaAlreadyExistsException("Ya existe una categoría con nombre: " + categoria.getNombre());

        } // if

        categoriaRepository.saveAndFlush(toEntity(categoria));
        return obtenerCategoriaPorNombre(categoria.getNombre());

    } // crearNuevaCategoria

    // Método para obtener todas las categorías

    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> categoriaDTOs = new ArrayList<>();

        if(!categorias.isEmpty()) for(Categoria c : categorias) categoriaDTOs.add(toDTO(c));

        return categoriaDTOs;

    }

    // Método para obtener una categoría por id 

    public CategoriaDTO obtenerCategoriaPorId(Long id) throws CategoriaNotFoundException {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new CategoriaNotFoundException("No se ha encontrado ninguna categoría con id: " + id));
    
        return toDTO(categoria);

    } 

    // Método para obtener una categoría por nombre 

    public CategoriaDTO obtenerCategoriaPorNombre(String nombre) throws CategoriaNotFoundException {
        Categoria categoria = categoriaRepository.findByNombre(nombre)
            .orElseThrow( () -> new CategoriaNotFoundException("No se ha encontrado ninguna categoría con nombre: " + nombre) );

        return toDTO(categoria);

    } // obtenerCategoriaPorNombre

    // Método para eliminar una categoría

    public void eliminarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findByNombre(categoriaDTO.getNombre())
            .orElseThrow(() -> new CategoriaNotFoundException("No se ha encontrado ninguna categoría con nombre: " + categoriaDTO.getNombre()));

        categoriaRepository.delete(categoria);
        System.out.println("La categoría: " + categoria.getNombre() + " se ha eliminado correctamente.");

    }

    // Comprobar si existe una categoría por id 

    public boolean categoriaExistePorId(Long id) {
        return categoriaRepository.existsById(id);
    }
    
    // Comprobar si existe categoría por nombre 

    public boolean categoriaExistePorNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);

    }

} // class