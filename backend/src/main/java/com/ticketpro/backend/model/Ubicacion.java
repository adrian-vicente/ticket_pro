package com.ticketpro.backend.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ubicaciones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ubicacion {

    // Declaración de atributos 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;

    @Column(name = "image_url")
    private String imageUrl;

    // Declaración de atributos para las relaciones 

    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private List<Evento> eventos;

} // class