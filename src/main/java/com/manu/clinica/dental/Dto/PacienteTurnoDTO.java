package com.manu.clinica.dental.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteTurnoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String documento;

    public PacienteTurnoDTO() {
    }

    public PacienteTurnoDTO(Long id, String nombre, String apellido, String documento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
    }
}
