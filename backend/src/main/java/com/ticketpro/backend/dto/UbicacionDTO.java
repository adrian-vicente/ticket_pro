package com.ticketpro.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UbicacionDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private String imageUrl;


} // class