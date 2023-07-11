package com.manu.clinica.dental.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manu.clinica.dental.Entity.Odontologo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoPacienteDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime hora;
    private Odontologo odontologo;

    public TurnoPacienteDTO(){}

    public TurnoPacienteDTO(Long id, LocalDate fecha, LocalTime hora, Odontologo odontologo) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.odontologo = odontologo;
    }
}
