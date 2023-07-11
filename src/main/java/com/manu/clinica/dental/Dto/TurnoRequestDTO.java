package com.manu.clinica.dental.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TurnoRequestDTO {
    private Long id;
    @NotNull(message = "La matricula no puede ser null")
    @NotBlank
    private String matricula;
    @NotNull(message = "El documento no puede ser null")
    @NotBlank
    private String documento;
    @NotNull(message = "La fecha no puede ser null")

    private LocalDate fecha;
    @NotNull(message = "La hora no puede ser null")

    private LocalTime hora;
}
