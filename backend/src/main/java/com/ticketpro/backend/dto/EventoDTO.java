package com.ticketpro.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import com.ticketpro.backend.model.EstadoEvento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    // Declaración de atributos

    private Long id;
    private Long proveedor_id;
    private Long categoria_id;
    private Long ubicacion_id;
    
    @NotBlank(message = "El evento debe tener un nombre asociado.")
    @Size(max = 150, message = "El nombre del evento debe tener 150 carácteres máximo.")
    private String nombre;

    @NotBlank(message = "El evento debe tener una descripción asociado.")
    private String descripcion;

    @NotNull(message = "El evento debe tener una fecha de realización.")
    private LocalDate fecha;

    @NotNull(message = "El evento debe tener una hora de inicio.")
    private LocalTime hora;

    @NotNull(message = "El evento debe tener una capacidad de asistentes máxima.")
    private Integer capacidad;

    @NotNull(message = "El evento debe de tener un estado asociado al mismo.")
    private EstadoEvento estado;

    @NotNull
    @PositiveOrZero(message = "El precio debe ser igual o superior a zero. ")
    private Double precio;

    private MultipartFile imagen;

} // class