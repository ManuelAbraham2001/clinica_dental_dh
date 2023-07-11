package com.manu.clinica.dental.Service;

import com.manu.clinica.dental.Dto.PacienteDTO;
import com.manu.clinica.dental.Entity.Paciente;
import com.manu.clinica.dental.Exceptions.PacienteAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PacienteService {
    Paciente crearPaciente(Paciente paciente) throws PacienteAlreadyExistsException, IllegalAccessException;
    Optional<Paciente> buscarPacientePorId(Long id) throws ResourceNotFoundException;
    Optional<Paciente> buscarPacientePorCorreo(String email) throws ResourceNotFoundException;
    Paciente buscarPacientePorDocumento(String documento) throws ResourceNotFoundException;
    void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException, PacienteAlreadyExistsException;
    void eliminarPaciente(Long id) throws ResourceNotFoundException;
    List<Paciente> listarTodosLosPacientes();
    Page<Paciente> ordenarPacientes(String orden, int nPagina);
    PacienteDTO listarTodosLosTurnosDelPaciente(Long id, int pagina);
    Page<Paciente> listarPacientesPaginados(Pageable pageable);
}
