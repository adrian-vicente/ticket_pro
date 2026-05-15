package com.ticketpro.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO {

    @Email(message = "El formato del mail no es correcto.")
    @NotBlank(message = "El proveedor debe tener un mail asociado.")
    private String email;

    @NotBlank(message = "El proveedor debe tener una contraseña obligatoriamente.")
    private String password;

} // class