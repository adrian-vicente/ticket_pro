package com.ticketpro.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDTO {

    private Long id;
    
    @NotBlank(message = "El proveedor debe tener un nombre asociado.")
    @Size(max = 150, message = "El nombre del proveedor debe tener 150 carácteres máximo.")
    private String nombre;

    @Email(message = "El formato del mail no es correcto.")
    @NotBlank(message = "El proveedor debe tener un mail asociado.")
    private String email;

    @Size(max = 20, message = "La longitud del teléfono es de 20 carácteres máximo.")
    private String telefono;

    @Size(max = 255, message = "La descripción debe tener 255 cómo máximo.")
    private String descripcion;

    @NotBlank(message = "El proveedor debe tener una contraseña obligatoriamente.")
    private String password;

    @NotNull(message = "El proveedor debe tener un estado activo o no.")
    private Boolean activo;

    private String empresa;
    private String web;

} // class