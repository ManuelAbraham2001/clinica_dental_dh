package com.manu.clinica.dental.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;



@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OdontologoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;
    private Page<TurnoOdontologoDTO> turnos;

    public OdontologoDTO(){}

    public OdontologoDTO(Long id, String nombre, String apellido, String matriula, Page<TurnoOdontologoDTO> turnos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matriula;
        this.turnos = turnos;
    }

    public OdontologoDTO(Long id, String nombre, String apellido, String matriula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matriula;
    }
}
