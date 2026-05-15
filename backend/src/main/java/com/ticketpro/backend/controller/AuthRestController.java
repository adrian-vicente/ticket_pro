package com.ticketpro.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketpro.backend.dto.LoginDTO;
import com.ticketpro.backend.service.LoginService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    // Inyección de dependencias

    private final LoginService loginService;
    public AuthRestController(LoginService loginService) {
        this.loginService = loginService;

    } // AuthRestController 

    // Método para iniciar sesión con proveedor

    @PostMapping("/login")
    public ResponseEntity<Long> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        Long proveedorId = loginService.loginProveedor(loginDTO);
        return ResponseEntity.ok(proveedorId);

    } // iniciarSesion

} // class