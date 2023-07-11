package com.manu.clinica.dental.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "Provincias")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotNull(message = "El nombre de la provincia no puede ser null")
    @NotBlank
    private String nombre;

    @OneToMany(mappedBy = "provincia")
    @JsonIgnore
    private List<Direccion> direcciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia() {
    }

    public Provincia(String nombre) {
        this.nombre = nombre;
    }
}
