package com.ticketpro.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ticketpro.backend.dto.LoginDTO;
import com.ticketpro.backend.exception.IncorrectPasswordException;
import com.ticketpro.backend.exception.ProveedorNotFoundException;
import com.ticketpro.backend.model.Proveedor;
import com.ticketpro.backend.repository.ProveedorRepository;

@Service
public class LoginService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorService proveedorService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(ProveedorRepository proveedorRepository, ProveedorService proveedorService, PasswordEncoder passwordEncoder) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorService = proveedorService;
        this.passwordEncoder = passwordEncoder;

    } // LoginService

    // Método para iniciar sesión a partir de email / password 

    public Long loginProveedor(LoginDTO loginDTO) throws Exception {
        Proveedor proveedor = proveedorRepository.findByEmail( loginDTO.getEmail() )
            .orElseThrow( () -> new ProveedorNotFoundException("No se ha encontrado ningún proveedor con el email: " + loginDTO.getEmail()) );

        if( !passwordEncoder.matches(loginDTO.getPassword(), proveedor.getPassword()) ) {
            throw new IncorrectPasswordException("La contraseña para el proveedor con email: " + loginDTO.getEmail() + " no es correcta.");

        } // if

        return proveedor.getId();

    } // loginProveedor

} // class