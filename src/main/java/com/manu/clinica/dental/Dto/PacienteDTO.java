package com.manu.clinica.dental.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manu.clinica.dental.Entity.Direccion;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String documento;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;

    private Direccion direccion;
    private Page<TurnoPacienteDTO> turnos;

    public PacienteDTO() {
    }

    public PacienteDTO(Long id, String nombre, String apellido, String documento, String email, LocalDate fechaIngreso, Direccion direccion, Page<TurnoPacienteDTO> turnos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.direccion = direccion;
        this.turnos = turnos;
    }
}
