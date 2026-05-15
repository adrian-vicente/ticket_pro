package com.ticketpro.backend.exception;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException(String mensaje) {
        super(mensaje);
    } 

} // class