package com.manu.clinica.dental.Service;

import com.manu.clinica.dental.Dto.TurnoDTO;
import com.manu.clinica.dental.Dto.TurnoRequestDTO;
import com.manu.clinica.dental.Entity.Turno;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Exceptions.TurnoAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.TurnoDateBeforeException;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TurnoService {
    Turno crearTurno(TurnoRequestDTO turnoRequestDTO) throws TurnoDateBeforeException, ResourceNotFoundException, ParseException, TurnoAlreadyExistsException;
    Optional<Turno> buscarTurnoPorId(Long id) throws ResourceNotFoundException;
    void actualizarTurno(TurnoRequestDTO turnoRequestDTO) throws TurnoDateBeforeException, ResourceNotFoundException, ParseException, TurnoAlreadyExistsException;
    void eliminarTurno(Long id) throws ResourceNotFoundException;
    List<TurnoDTO> listarTodosLosTurnos();
    Page<TurnoDTO> listarTurnosPaginados(int pagina);
    Page<TurnoDTO> listarTurnosPorFechaPaginados(int pagina, LocalDate fecha);
    Page<TurnoDTO> listarTurnosPorPaciente(int pagina, String documento);
    Page<TurnoDTO> listarTurnosPorOdontologo(int pagina, String matricula);

    Page<TurnoDTO> listarTurnosOdenados(String orden, int pagina);



}
