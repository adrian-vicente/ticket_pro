package com.ticketpro.backend.exception;

public class ProveedorEmailAlreadyExistsException extends RuntimeException {

    public ProveedorEmailAlreadyExistsException(String mensaje) {
        super(mensaje);

    }

} // class