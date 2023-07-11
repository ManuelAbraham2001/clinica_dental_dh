package com.manu.clinica.dental.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "turnos")
@Getter
@Setter
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @NotNull(message = "La fecha no puede ser null")
    private LocalDate fecha;

    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    @NotNull(message = "La hora no puede ser null")
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @NotNull(message = "El paciente no puede ser null")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "odontologo_id")
    @NotNull(message = "El odontologo no puede ser null")
    private Odontologo odontologo;

    public Turno(){}

    public Turno(Long id, LocalDate fecha, Paciente paciente, Odontologo odontologo, LocalTime hora) {
        this.id = id;
        this.fecha = fecha;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.hora = hora;
    }

    public Turno(LocalDate fecha, Paciente paciente, Odontologo odontologo, LocalTime hora) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.hora = hora;
    }
}
