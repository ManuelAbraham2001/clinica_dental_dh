package com.manu.clinica.dental.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")

    private LocalDate fecha;
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime hora;
    @JsonProperty("paciente")
    private PacienteTurnoDTO pacienteTurnoDTO;
    @JsonIgnoreProperties("turnos")
    @JsonProperty("odontologo")
    private OdontologoDTO odontologoDTO;

    public TurnoDTO(){}

    public TurnoDTO(Long id, LocalDate fecha, LocalTime hora, PacienteTurnoDTO pacienteTurnoDTO, OdontologoDTO odontologoDTO) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.pacienteTurnoDTO = pacienteTurnoDTO;
        this.odontologoDTO = odontologoDTO;
    }
}
