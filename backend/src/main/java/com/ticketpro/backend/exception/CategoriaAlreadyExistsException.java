package com.ticketpro.backend.exception;

public class CategoriaAlreadyExistsException extends RuntimeException {

    public CategoriaAlreadyExistsException(String mensaje) {
        super(mensaje);
    }

} // class