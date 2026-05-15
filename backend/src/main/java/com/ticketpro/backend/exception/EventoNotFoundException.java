package com.ticketpro.backend.exception;

public class EventoNotFoundException extends RuntimeException {

    public EventoNotFoundException(String mensaje) {
        super(mensaje);
    }

} // class