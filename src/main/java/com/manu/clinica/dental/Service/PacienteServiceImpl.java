package com.manu.clinica.dental.Service;


import com.manu.clinica.dental.Dto.PacienteDTO;
import com.manu.clinica.dental.Dto.TurnoPacienteDTO;
import com.manu.clinica.dental.Entity.Paciente;
import com.manu.clinica.dental.Entity.Turno;
import com.manu.clinica.dental.Exceptions.PacienteAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Repository.PacienteRepository;
import com.manu.clinica.dental.Repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class PacienteServiceImpl implements PacienteService{

    @Autowired
    private PacienteRepository repo;

    @Autowired
    private TurnoRepository turnoRepository;

    @Override
    public Paciente crearPaciente(Paciente paciente) throws PacienteAlreadyExistsException {

        Optional<Paciente> pacienteDcomento = repo.findByDocumento(paciente.getDocumento());
        Optional<Paciente> pacienteEmail = repo.findByEmail(paciente.getEmail());

        if(pacienteDcomento.isPresent()){
            throw new PacienteAlreadyExistsException("Ya existe un paciente con el documento: " + paciente.getDocumento());
        }
        if(pacienteEmail.isPresent()){
            throw new PacienteAlreadyExistsException("Ya existe un paciente con el email: " + paciente.getEmail());
        }

        return repo.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPacientePorId(Long id) throws ResourceNotFoundException {

        Optional<Paciente> pacienteDB = repo.findById(id);

        if(pacienteDB.isEmpty()){
            throw new ResourceNotFoundException("No existe un paciente con el id: " + id);
        }

        return pacienteDB;
    }

    @Override
    public Optional<Paciente> buscarPacientePorCorreo(String email) throws ResourceNotFoundException {

        Optional<Paciente> pacienteDB = repo.findByEmail(email);

        if(pacienteDB.isEmpty()){
            throw new ResourceNotFoundException("No existe un paciente con el correo: " + email);
        }

        return pacienteDB;
    }

    @Override
    public Paciente buscarPacientePorDocumento(String documento) throws ResourceNotFoundException {

        Optional<Paciente> paciente = repo.findByDocumento(documento);

        if(paciente.isPresent()){
            return paciente.get();
        }else{
            throw new ResourceNotFoundException("No existe ningun paciente con el documento: " + documento);
        }

    }

    @Override
    public void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException, PacienteAlreadyExistsException {

        buscarPacientePorId(paciente.getId());
        Optional<Paciente> pacienteDBdocumento = repo.findByDocumento(paciente.getDocumento());
        Optional<Paciente> pacienteDBemail = repo.findByEmail(paciente.getEmail());

        if(pacienteDBdocumento.isPresent() || pacienteDBemail.isPresent()){
            if(pacienteDBdocumento.get().getDocumento().equals(paciente.getDocumento()) && paciente.getId() != pacienteDBdocumento.get().getId() || pacienteDBemail.get().getEmail().equals(paciente.getEmail()) && paciente.getId() != pacienteDBemail.get().getId()){
                throw new PacienteAlreadyExistsException("El documento o el email ya estan en uso");
            }
        }else{
            throw new ResourceNotFoundException("No se encontro el paciente");
        }

        repo.save(paciente);

    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        buscarPacientePorId(id);
        repo.deleteById(id);
    }

    @Override
    public List<Paciente> listarTodosLosPacientes() {
        return repo.findAll();
    }

    @Override
    public Page<Paciente> ordenarPacientes(String orden, int nPagina) {
        int cantidadPorPagina = 10;
        Sort sort = switch (orden) {
            case "apellidoASC" -> Sort.by(Sort.Direction.ASC, "apellido");
            case "apellidoDESC" -> Sort.by(Sort.Direction.DESC, "apellido");
            case "fechaIngresoASC" -> Sort.by(Sort.Direction.ASC, "fechaIngreso");
            case "fechaIngresoDESC" -> Sort.by(Sort.Direction.DESC, "fechaIngreso");
            default -> Sort.by(Sort.DEFAULT_DIRECTION, "id");
        };

        Pageable pageable = PageRequest.of(nPagina - 1, cantidadPorPagina, sort);
        return repo.listarPacientesPaginados(pageable);
    }

    @Override
    public Page<Paciente> listarPacientesPaginados(Pageable pageable) {
        return repo.listarPacientesPaginados(pageable);
    }

    @Override
    public PacienteDTO listarTodosLosTurnosDelPaciente(Long id, int pagina){

        Optional<Paciente> pacienteDB = repo.findById(id);
        Paciente paciente = null;

        if(pacienteDB.isPresent()){
            paciente = pacienteDB.get();
        }

        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(pagina - 1, cantidadPorPagina);

        Page<Turno> turnosPaciente = turnoRepository.listarTurnosDelPacientePaginados(id, pageable);
        Set<TurnoPacienteDTO> turnoPacienteDTO = new HashSet<>();

        for (Turno t : turnosPaciente) {
            turnoPacienteDTO.add(new TurnoPacienteDTO(t.getId(), t.getFecha(), t.getHora(), t.getOdontologo()));
        }

        Page<TurnoPacienteDTO> turnoPacienteDTOPage = new PageImpl<>(turnoPacienteDTO.stream().toList(), pageable, turnosPaciente.getTotalPages());

        return new PacienteDTO(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getDocumento(), paciente.getEmail(), paciente.getFechaIngreso(), paciente.getDireccion(), turnoPacienteDTOPage);
    }



}
