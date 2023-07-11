package com.manu.clinica.dental.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La calle no puede ser null")
    @NotBlank
    private String calle;

    @NotNull(message = "El numero no puede ser null")
    @NotBlank
    private String numero;

    @NotNull(message = "La localidad no puede ser null")
    @NotBlank
    private String localidad;

    @NotNull(message = "La provincia no puede ser null")
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;
}
