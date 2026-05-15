package com.ticketpro.backend.exception;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ticketpro.backend.model.Proveedor;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<String> categoriaNotFound(CategoriaNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());

    } // categoriaNotFound

    @ExceptionHandler(UbicacionNotFoundException.class)
    public ResponseEntity<String> ubicacionNotFound(UbicacionNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());

    } // ubicacionNotFound

    @ExceptionHandler(ProveedorNotFoundException.class)
    public ResponseEntity<String> proveedorNotFound(ProveedorNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());

    } // proveedorNotFound

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> incorrectPassword(IncorrectPasswordException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ex.getMessage());

    } // incorrectPassword

    @ExceptionHandler(ProveedorAlreadyExistsException.class)
    public ResponseEntity<String> proveedorAlreadyExists(ProveedorAlreadyExistsException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());

    }

    @ExceptionHandler(ProveedorEmailAlreadyExistsException.class)
    public ResponseEntity<String> proveedorEmailAlreadyExists(ProveedorEmailAlreadyExistsException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
            
    } 

    @ExceptionHandler(EventoNotFoundException.class)
    public ResponseEntity<String> eventoNotFoundException(EventoNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());

    } // eventoNotFoundException

    @ExceptionHandler(CategoriaAlreadyExistsException.class)
    public ResponseEntity<String> categoriaAlreadyExists(CategoriaAlreadyExistsException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
    }

    @ExceptionHandler(UbicacionAlreadyExistsException.class)
    public ResponseEntity<String> ubicacionAlreadyExists(UbicacionAlreadyExistsException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
    }

} // class