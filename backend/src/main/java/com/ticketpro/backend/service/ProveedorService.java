package com.ticketpro.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ticketpro.backend.config.ValidationConfig;
import com.ticketpro.backend.dto.ProveedorDTO;
import com.ticketpro.backend.exception.ProveedorAlreadyExistsException;
import com.ticketpro.backend.exception.ProveedorEmailAlreadyExistsException;
import com.ticketpro.backend.exception.ProveedorNotFoundException;
import com.ticketpro.backend.model.Proveedor;
import com.ticketpro.backend.repository.ProveedorRepository;


@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ValidationConfig validationConfig;
    private final PasswordEncoder passwordEncoder;

    public ProveedorService(ProveedorRepository proveedorRepository, ValidationConfig validationConfig, 
        PasswordEncoder passwordEncoder
    ) {
        this.proveedorRepository = proveedorRepository;
        this.validationConfig = validationConfig;
        this.passwordEncoder = passwordEncoder;

    }

    public Proveedor toEntity(ProveedorDTO proveedorDTO) {

        Proveedor proveedor = new Proveedor();
        
        if( !validationConfig.isNullOrEqualsZero(proveedorDTO.getId()) ) {
            proveedor.setId(proveedorDTO.getId()); 

        } // if

        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setDescripcion(proveedorDTO.getDescripcion());
        proveedor.setActivo(proveedorDTO.getActivo());
        proveedor.setEmpresa(proveedorDTO.getEmpresa());
        proveedor.setWeb(proveedorDTO.getWeb());

            return proveedor;


    } // toEntity

    public ProveedorDTO toDTO(Proveedor proveedor) {

        ProveedorDTO proveedorDTO = new ProveedorDTO();

        if( !validationConfig.isNullOrEqualsZero(proveedor.getId()) ) {
            proveedorDTO.setId(proveedor.getId());

        } // if

        proveedorDTO.setNombre(proveedor.getNombre());
        proveedorDTO.setEmail(proveedor.getEmail());
        proveedorDTO.setTelefono(proveedor.getTelefono());
        proveedorDTO.setDescripcion(proveedor.getDescripcion());
        proveedorDTO.setActivo(proveedor.getActivo());
        proveedorDTO.setEmpresa(proveedor.getEmpresa());
        proveedorDTO.setWeb(proveedor.getWeb());

        return proveedorDTO;

    } // toDTO

    // Método para comprobar si existe proveedor por email en la base de datos 

    @Transactional(readOnly = true)
    public boolean proveedorExistePorEmail(ProveedorDTO proveedorDTO) {
        return proveedorRepository.existsByEmail(proveedorDTO.getEmail());

    } 

    // Método para crear un proveedor en la base de datos 

    @Transactional
    public ProveedorDTO crearNuevoProveedor(ProveedorDTO proveedorDTO) throws ProveedorAlreadyExistsException {
        
        Proveedor proveedor = null; Proveedor proveedorGuardado = null;
        
        if( proveedorExistePorEmail(proveedorDTO) ) throw new ProveedorAlreadyExistsException("Ya existe un proveedor con el email: " + proveedorDTO.getEmail() + " registrado en la base de datos.");
        else {
            proveedor = toEntity( proveedorDTO );
            proveedor.setPassword( passwordEncoder.encode( proveedorDTO.getPassword() ) );
            proveedorGuardado = proveedorRepository.save( proveedor );

            return toDTO( proveedorGuardado );

        } // if - else

    } // crearNuevoProveedor`

    // Método para obtener el proveedor por identificador 

    @Transactional(readOnly = true)
    public ProveedorDTO obtenerProveedorPorId(Long id) throws ProveedorNotFoundException {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow(() -> new ProveedorNotFoundException("No se ha encontrado a ningún proveedor con el id: " + id));

        System.out.println("Se ha encontrado proveedor con el id: " + id);
        return toDTO(proveedor);

    } // obtenerProveedorPorId

    // Método para actualizar los datos de un proveedor 

    @Transactional
    public ProveedorDTO modificarDatosProveedor(ProveedorDTO proveedorDTO, Long proveedor_id) throws Exception {
        Proveedor proveedor = proveedorRepository.findById(proveedor_id)
            .orElseThrow( () -> new ProveedorNotFoundException("No se ha encontrado ningún proveedor con id: " + proveedor_id) );

        // Comprobaciones y modificaciones sobre la password

        if(proveedorDTO.getPassword() != null && !proveedorDTO.getPassword().isBlank()) {
            proveedor.setPassword(passwordEncoder.encode(proveedorDTO.getPassword()));

        } // if

        // Comprobar si hay nuevo email y en ese caso ver que no exista en la base de datos. 

        if( !proveedor.getEmail().equals(proveedorDTO.getEmail()) && proveedorExistePorEmail(proveedorDTO) ) {
            throw new ProveedorEmailAlreadyExistsException("Ya existe otro proveedor con el siguiente email: " + proveedorDTO.getEmail());

        } else {
            System.out.println("Se ha verificado el email. No existe otro igual en la base de datos.");
            proveedor.setEmail( proveedorDTO.getEmail() );

        } // if - else

        // Modificar los datos del proveedor

        proveedor.setNombre( proveedorDTO.getNombre() );
        proveedor.setTelefono( proveedorDTO.getTelefono() );
        proveedor.setDescripcion( proveedorDTO.getDescripcion() );
        proveedor.setActivo( proveedorDTO.getActivo() );
        proveedor.setEmpresa( proveedorDTO.getEmpresa() );
        proveedor.setWeb( proveedorDTO.getWeb() );

        // Devolver el proveedor obtenido pero con los datos modificados

        return toDTO( proveedor );

    } // modificarDatosProveedor

    // Método para desactivar un proveedor

    @Transactional
    public ProveedorDTO desactivarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow( () -> new ProveedorNotFoundException("No se ha encontrado el proveedor, por lo tanto no se puede desactivar.") );

        proveedor.setActivo(false);
        System.out.println("El estado del proveedor: " + proveedor.getNombre() + " se ha modificado correctamente. Ahora está desactivado.");
        return toDTO( proveedor );

    } // desactivarProveedor

    // Método para activar un proveedor nuevamente 

    @Transactional
    public ProveedorDTO activarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow( () -> new ProveedorNotFoundException("No se ha encontrado el proveedor, por lo tanto no se activará ninguno ya desactivado.") );

        proveedor.setActivo(true);
        System.out.println("El estado del proveedor: " + proveedor.getNombre() + " se ha modificado correctamente. Ahora está activo de nuevo.");
        return toDTO( proveedor );

    } // activarProveedor

    // Método para obtener la lista general de proveedores. 

    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        List<ProveedorDTO> proveedoresDTO = new ArrayList<ProveedorDTO>();

        if( !proveedores.isEmpty() ) {
            for(Proveedor prov : proveedores)  proveedoresDTO.add( toDTO(prov) );

        } // if

        return proveedoresDTO;

    } // obtenerTodosLosProveedores

    // Obtener proveedor a partir del email 

    @Transactional
    public ProveedorDTO obtenerProveedorPorEmail(String email) {
        Proveedor proveedor = proveedorRepository.findByEmail(email)
            .orElseThrow( () -> new ProveedorNotFoundException("No se ha encontrado ningún proveedor con email: " + email) );

        System.out.println("Se ha encontrado correctamente el proveedor con nombre: " + proveedor.getNombre() + ", con email: " + email);

        return toDTO( proveedor );

    } // obtenerProveedorPorEmail

} // class