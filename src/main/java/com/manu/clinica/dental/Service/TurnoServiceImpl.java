package com.manu.clinica.dental.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.manu.clinica.dental.Dto.*;
import com.manu.clinica.dental.Entity.Odontologo;
import com.manu.clinica.dental.Entity.Paciente;
import com.manu.clinica.dental.Entity.Turno;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Exceptions.TurnoAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.TurnoDateBeforeException;
import com.manu.clinica.dental.Repository.OdontologoRepository;
import com.manu.clinica.dental.Repository.PacienteRepository;
import com.manu.clinica.dental.Repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoServiceImpl implements TurnoService{

    @Autowired
    private TurnoRepository repo;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static{
       objectMapper.registerModule(new JavaTimeModule());
    }


    @Override
    public Turno crearTurno(TurnoRequestDTO turnoRequestDTO) throws TurnoDateBeforeException, ResourceNotFoundException, TurnoAlreadyExistsException {

        Optional<Paciente> paciente = pacienteRepository.findByDocumento(turnoRequestDTO.getDocumento());
        Optional<Odontologo> odontologo = odontologoRepository.findByMatricula(turnoRequestDTO.getMatricula());

        if(paciente.isPresent() && odontologo.isPresent() && turnoRequestDTO.getFecha().isAfter(LocalDate.now())){
            List<Turno> turnosEnLaFecha = repo.findAllByFecha(turnoRequestDTO.getFecha());

            boolean turnoExistente = turnosEnLaFecha.stream()
                    .anyMatch(t -> t.getHora().equals(turnoRequestDTO.getHora()) && t.getOdontologo().getMatricula().equals(turnoRequestDTO.getMatricula()));

            if (turnoExistente) {
                throw new TurnoAlreadyExistsException("Ya existe un turno en esa fecha con ese horario con el odontologo " + odontologo.get().getNombre());
            }
            Turno turno = new Turno(turnoRequestDTO.getFecha(), paciente.get(), odontologo.get(), turnoRequestDTO.getHora());
            return repo.save(turno);
        }else if(paciente.isEmpty()){
            throw new ResourceNotFoundException("El paciente no existe");
        }else if(odontologo.isEmpty()){
            throw new ResourceNotFoundException("El odontologo no existe");
        }else{
            throw new TurnoDateBeforeException("La fecha del turno no puede ser anterior a la fecha actual");
        }

        // repensar esta logica

    }

    @Override
    public Optional<Turno> buscarTurnoPorId(Long id) throws ResourceNotFoundException {

        Optional<Turno> turno = repo.findById(id);

        if(turno.isEmpty()){
            throw new ResourceNotFoundException("No existe un turno con el id: " + id);
        }

        return turno;
    }

    @Override
    public void actualizarTurno(TurnoRequestDTO turnoRequestDTO) throws TurnoDateBeforeException, ResourceNotFoundException, TurnoAlreadyExistsException {

        Optional<Turno> turnoDB = buscarTurnoPorId(turnoRequestDTO.getId());
        Optional<Paciente> paciente = pacienteRepository.findByDocumento(turnoRequestDTO.getDocumento());
        Optional<Odontologo> odontologo = odontologoRepository.findByMatricula(turnoRequestDTO.getMatricula());

        if(turnoDB.isPresent() && paciente.isPresent() && odontologo.isPresent() && turnoRequestDTO.getFecha().isAfter(LocalDate.now())){
            List<Turno> turnosEnLaFecha = repo.findAllByFecha(turnoRequestDTO.getFecha());
            Turno turnoExistenteEnLaFecha = null;

            for (Turno t : turnosEnLaFecha) {
                if(t.getHora().equals(turnoRequestDTO.getHora()) && t.getOdontologo().getMatricula().equals(turnoRequestDTO.getMatricula())){
                    turnoExistenteEnLaFecha = t;
                    break;
                }
            }

            if (turnoExistenteEnLaFecha != null && turnoExistenteEnLaFecha.getId() != turnoRequestDTO.getId()) {
                throw new TurnoAlreadyExistsException("Ya existe un turno en esa fecha con ese horario con el odontologo " + odontologo.get().getNombre());
            }

            repo.save(new Turno(turnoDB.get().getId(), turnoRequestDTO.getFecha(), paciente.get(), odontologo.get(), turnoRequestDTO.getHora()));

        }else if(paciente.isEmpty()){
            throw new ResourceNotFoundException("El paciente no existe");
        }else if(odontologo.isEmpty()){
            throw new ResourceNotFoundException("El odontologo no existe");
        }else{
            throw new TurnoDateBeforeException("La fecha del turno no puede ser anterior a la fecha actual");
        }

    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        buscarTurnoPorId(id);
        repo.deleteById(id);
    }

    @Override
    public List<TurnoDTO> listarTodosLosTurnos() {

        List<Turno> turnos = repo.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();

        for (Turno t:turnos) {
//            turnosDTO.add((TurnoDTO) MapperDTO.convertToDTO(t));
            PacienteTurnoDTO pacienteTurnoDTO = objectMapper.convertValue(t.getPaciente(), PacienteTurnoDTO.class);
            OdontologoDTO odontologoDTO = objectMapper.convertValue(t.getOdontologo(), OdontologoDTO.class);
            turnosDTO.add(new TurnoDTO(t.getId(), t.getFecha(), t.getHora(), pacienteTurnoDTO, odontologoDTO));
        }

        return turnosDTO;
    }

    @Override
    public Page<TurnoDTO> listarTurnosPaginados(int pagina) {
        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(pagina - 1, cantidadPorPagina);

        Page<Turno> turnos = repo.turnosPaginados(pageable);
        List<TurnoDTO> turnosDTOlist = new ArrayList<>();

        for (Turno t : turnos) {
            PacienteTurnoDTO pacienteTurnoDTO = objectMapper.convertValue(t.getPaciente(), PacienteTurnoDTO.class);
            OdontologoDTO odontologoDTO = objectMapper.convertValue(t.getOdontologo(), OdontologoDTO.class);
//            turnosDTOlist.add((TurnoDTO) MapperDTO.convertToDTO(t));
            turnosDTOlist.add(new TurnoDTO(t.getId(), t.getFecha(), t.getHora(), pacienteTurnoDTO, odontologoDTO));
        }

        return new PageImpl<>(turnosDTOlist, pageable, turnos.getTotalElements());
    }

    @Override
    public Page<TurnoDTO> listarTurnosPorFechaPaginados(int pagina, LocalDate fecha) {
        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(pagina - 1, cantidadPorPagina);

        Page<Turno> turnos = repo.turnosPaginadosPorFecha(pageable, fecha);
        List<TurnoDTO> turnosDTOlist = new ArrayList<>();

        for (Turno t : turnos) {
            PacienteTurnoDTO pacienteTurnoDTO = objectMapper.convertValue(t.getPaciente(), PacienteTurnoDTO.class);
            OdontologoDTO odontologoDTO = objectMapper.convertValue(t.getOdontologo(), OdontologoDTO.class);
//            turnosDTOlist.add((TurnoDTO) MapperDTO.convertToDTO(t));
            turnosDTOlist.add(new TurnoDTO(t.getId(), t.getFecha(), t.getHora(), pacienteTurnoDTO, odontologoDTO));
        }

        return new PageImpl<>(turnosDTOlist, pageable, turnos.getTotalElements());
    }

    @Override
    public Page<TurnoDTO> listarTurnosPorPaciente(int pagina, String documento) {
        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(pagina - 1, cantidadPorPagina);
        Page<Turno> turnos = repo.turnosPaginadosPorPaciente(pageable, documento);
        List<TurnoDTO> turnosDTOlist = new ArrayList<>();

        for (Turno t : turnos) {
            PacienteTurnoDTO pacienteTurnoDTO = objectMapper.convertValue(t.getPaciente(), PacienteTurnoDTO.class);
            OdontologoDTO odontologoDTO = objectMapper.convertValue(t.getOdontologo(), OdontologoDTO.class);
//            turnosDTOlist.add((TurnoDTO) MapperDTO.convertToDTO(t));
            turnosDTOlist.add(new TurnoDTO(t.getId(), t.getFecha(), t.getHora(), pacienteTurnoDTO, odontologoDTO));
        }

        return new PageImpl<>(turnosDTOlist, pageable, turnos.getTotalElements());
    }

    @Override
    public Page<TurnoDTO> listarTurnosPorOdontologo(int pagina, String matricula) {
        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(pagina - 1, cantidadPorPagina);
        Page<Turno> turnos = repo.turnosPaginadosPorOdontologo(pageable, matricula);
        List<TurnoDTO> turnosDTOlist = new ArrayList<>();

        for (Turno t : turnos) {
            PacienteTurnoDTO pacienteTurnoDTO = objectMapper.convertValue(t.getPaciente(), PacienteTurnoDTO.class);
            OdontologoDTO odontologoDTO = objectMapper.convertValue(t.getOdontologo(), OdontologoDTO.class);
//            turnosDTOlist.add((TurnoDTO) MapperDTO.convertToDTO(t));
            turnosDTOlist.add(new TurnoDTO(t.getId(), t.getFecha(), t.getHora(), pacienteTurnoDTO, odontologoDTO));
        }

        return new PageImpl<>(turnosDTOlist, pageable, turnos.getTotalElements());
    }

    @Override
    public Page<TurnoDTO> listarTurnosOdenados(String orden, int pagina) {

        int cantidadPorPagina = 10;
        Sort sort = switch (orden) {
            case "fechaASC" -> Sort.by(Sort.Direction.ASC, "fecha");
            case "fechaDESC" -> Sort.by(Sort.Direction.DESC, "fecha");
            default ->  Sort.by(Sort.DEFAULT_DIRECTION, "id");
        };

        Pageable pageable = PageRequest.of(pagina - 1, cantidadPorPagina, sort);
        Page<Turno> turnos = repo.turnosPaginados(pageable);

        List<TurnoDTO> turnosDTOlist = new ArrayList<>();

        for (Turno t : turnos) {
            PacienteTurnoDTO pacienteTurnoDTO = objectMapper.convertValue(t.getPaciente(), PacienteTurnoDTO.class);
            OdontologoDTO odontologoDTO = objectMapper.convertValue(t.getOdontologo(), OdontologoDTO.class);
//            turnosDTOlist.add((TurnoDTO) MapperDTO.convertToDTO(t));
            turnosDTOlist.add(new TurnoDTO(t.getId(), t.getFecha(), t.getHora(), pacienteTurnoDTO, odontologoDTO));
        }

        return new PageImpl<>(turnosDTOlist, pageable, turnos.getTotalElements());


    }


}
