package com.manu.clinica.dental.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TurnoOdontologoDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime hora;

    @JsonProperty("paciente")
    private PacienteTurnoDTO pacienteTurnoDTO;

    public TurnoOdontologoDTO(){};

    public TurnoOdontologoDTO(Long id, LocalDate fecha, PacienteTurnoDTO pacienteTurnoDTO, LocalTime hora) {
        this.id = id;
        this.fecha = fecha;
        this.pacienteTurnoDTO = pacienteTurnoDTO;
        this.hora = hora;
    }
}
