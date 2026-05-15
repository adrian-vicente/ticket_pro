package com.ticketpro.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ticketpro.backend.config.ValidationConfig;
import com.ticketpro.backend.dto.UbicacionDTO;
import com.ticketpro.backend.exception.UbicacionAlreadyExistsException;
import com.ticketpro.backend.exception.UbicacionNotFoundException;
import com.ticketpro.backend.model.Ubicacion;
import com.ticketpro.backend.repository.UbicacionRepository;

@Service
public class UbicacionService {

    // Inyección de dependencias

    private final UbicacionRepository ubicacionRepository;
    private final ValidationConfig validationConfig;

    public UbicacionService(UbicacionRepository ubicacionRepository, ValidationConfig validationConfig) {
        this.ubicacionRepository = ubicacionRepository;
        this.validationConfig = validationConfig;

    } 

    // Método de conversión a entidad

    public Ubicacion toEntity(UbicacionDTO ubicacionDTO) {

        Ubicacion ubicacion = new Ubicacion();

        if( !validationConfig.isNullOrEqualsZero(ubicacionDTO.getId()) ) {
            ubicacion.setId(ubicacionDTO.getId());

        } // if

        ubicacion.setNombre(ubicacionDTO.getNombre());
        ubicacion.setDireccion(ubicacionDTO.getDireccion());
        ubicacion.setCiudad(ubicacionDTO.getCiudad());
        ubicacion.setPais(ubicacionDTO.getPais());
        ubicacion.setImageUrl(ubicacionDTO.getImageUrl());

        return ubicacion;

    } // toEntity

    public UbicacionDTO toDTO(Ubicacion ubicacion) {
        
        UbicacionDTO ubicacionDTO = new UbicacionDTO();

        if( !validationConfig.isNullOrEqualsZero(ubicacion.getId()) ) {
            ubicacionDTO.setId(ubicacion.getId());

        } // if

        ubicacionDTO.setNombre(ubicacion.getNombre());
        ubicacionDTO.setDireccion(ubicacion.getDireccion());
        ubicacionDTO.setCiudad(ubicacion.getCiudad());
        ubicacionDTO.setPais(ubicacion.getPais());
        ubicacionDTO.setImageUrl(ubicacion.getImageUrl());

        return ubicacionDTO;

    } // toDTO

    // Método para crear una ubicación 

    public UbicacionDTO crearNuevaUbicacion(UbicacionDTO ubicacionDTO) throws UbicacionAlreadyExistsException {
        if(ubicacionExistePorNombre(ubicacionDTO.getNombre())) {
            throw new UbicacionAlreadyExistsException("Ya existe una ubicación con nombre: " + ubicacionDTO.getNombre());

        } // if

        ubicacionRepository.save(toEntity(ubicacionDTO));
        System.out.println("Se ha creado correctamente la ubicación: " + ubicacionDTO.getNombre());

        return obtenerUbicacionPorNombre(ubicacionDTO.getNombre());

    } // crearNuevaUbicacion

    // Método para obtener una ubicación por nombre 

    public UbicacionDTO obtenerUbicacionPorNombre(String nombre) {
        Ubicacion ubicacion = ubicacionRepository.findByNombre(nombre)
            .orElseThrow( () -> new UbicacionNotFoundException("No se ha encontrado ubicación con nombre: " + nombre) );

        System.out.println("Se ha encontrado la ubicación con nombre: " + nombre);
        return toDTO(ubicacion);

    } // obtenerUbicacionPorNombre

    // Método para obtener todas las ubicaciones 

    public List<UbicacionDTO> obtenerTodasLasUbicaciones() {
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        List<UbicacionDTO> ubicacionDTOs = new ArrayList<>();

        if(!ubicaciones.isEmpty()) for(Ubicacion ubi : ubicaciones) ubicacionDTOs.add(toDTO(ubi));

        return ubicacionDTOs;

    } // obtenerTodasLasUbicaciones

    // Método para obtener una ubicación por id 

    public UbicacionDTO obtenerUbicacionPorId(Long id) throws UbicacionNotFoundException {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
            .orElseThrow( () -> new UbicacionNotFoundException("No se ha encontrado ninguna ubicación con id: " + id) );

        System.out.println("Se ha encontrado correctamente la ubicación con id: " + id);
        return toDTO(ubicacion);

    } // obtenerUbicacionPorId

    // Método para eliminar una ubicación 

    public void eliminarUbicacion(UbicacionDTO ubicacionDTO) throws UbicacionNotFoundException  {
        if(!ubicacionExistePorNombre(ubicacionDTO.getNombre())) {
            throw new UbicacionNotFoundException("No se ha encontrado ninguna ubicación con nombre: " + ubicacionDTO.getNombre());

        } // if

        Ubicacion ubicacion = toEntity(obtenerUbicacionPorNombre(ubicacionDTO.getNombre()));
        ubicacionRepository.delete(ubicacion);
        System.out.println("La ubicación: " + ubicacion.getNombre() + " se ha eliminado correctamente.");

    } // if

    // Comprobar si existe una ubicación por id

    public boolean ubicacionExistePorId(Long id) {
        return ubicacionRepository.existsById(id);
    }

    // Comprobar si existe una ubicación por nombre 

    public boolean ubicacionExistePorNombre(String nombre) {
        return ubicacionRepository.existsByNombre(nombre);

    } // ubicacionExistePorNombre

} // class