package com.manu.clinica.dental.Service;

import com.manu.clinica.dental.Dto.OdontologoDTO;
import com.manu.clinica.dental.Dto.PacienteTurnoDTO;
import com.manu.clinica.dental.Dto.TurnoOdontologoDTO;
import com.manu.clinica.dental.Entity.Odontologo;
import com.manu.clinica.dental.Entity.Paciente;
import com.manu.clinica.dental.Entity.Turno;
import com.manu.clinica.dental.Exceptions.OdontologoAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Repository.OdontologoRepository;
import com.manu.clinica.dental.Repository.PacienteRepository;
import com.manu.clinica.dental.Repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OdontologoServiceImpl implements OdontologoService{

    @Autowired
    private OdontologoRepository repo;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Override
    public Odontologo crearOdontologo(Odontologo odontologo) throws OdontologoAlreadyExistsException, ResourceNotFoundException {

        Optional<Odontologo> odontologoDB = repo.findByMatricula(odontologo.getMatricula());

        if(odontologoDB.isPresent()){
            throw new OdontologoAlreadyExistsException("Ya existe un odontologo con la matricula: " + odontologo.getMatricula());
        }

        return repo.save(odontologo);
    }

    @Override
    public Odontologo buscarOdontologoPorId(Long id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("no existe un odontologo con el id: " + id));
    }

    @Override
    public Odontologo buscarOdontologoPorMatricula(String matricula) throws ResourceNotFoundException {
        return repo.findByMatricula(matricula).orElseThrow(() -> new ResourceNotFoundException("No se encontro un odontologo con la matricula: " + matricula));
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException, OdontologoAlreadyExistsException {
        buscarOdontologoPorId(odontologo.getId());

        Optional<Odontologo> odontologoDB = repo.findByMatricula(odontologo.getMatricula());

        if(odontologoDB.isPresent() && odontologo.getId() != odontologoDB.get().getId()){
            throw new OdontologoAlreadyExistsException("Ya existe un odontologo con la matricula: " + odontologo.getMatricula());
        }

        repo.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        buscarOdontologoPorId(id);
        repo.deleteById(id);
    }

    @Override
    public List<Odontologo> listarTodosLosOdontologos() {
        return repo.findAll();
    }

    @Override
    public Page<Odontologo> listarOdontologosPorPagina(Pageable pageable) {
        return repo.listarOdonotlogosPorPagina(pageable);
    }

    @Override
    public Page<Odontologo> ordenarOdontologos(String orden, int nPagina) {
        int cantidadPorPagina = 10;
        Sort sort = switch (orden) {
            case "apellidoASC" -> Sort.by(Sort.Direction.ASC, "apellido");
            case "apellidoDESC" -> Sort.by(Sort.Direction.DESC, "apellido");
            default -> Sort.by(Sort.DEFAULT_DIRECTION, "id");
        };

        Pageable pageable = PageRequest.of(nPagina - 1, cantidadPorPagina, sort);
        return repo.listarOdonotlogosPorPagina(pageable);
    }

    @Override
    public OdontologoDTO listarTurnosPorOdontologo(Long id, Pageable pageable) throws ResourceNotFoundException {

        Odontologo odontologoDB = buscarOdontologoPorId(id);
        Page<Turno> turnosOdontologo = turnoRepository.listarTurnosPorOdontologo(id, pageable);
        Set<TurnoOdontologoDTO> turnoOdontologoDTOS = new HashSet<>();

        for (Turno t : turnosOdontologo){
            Optional<Paciente> paciente = pacienteRepository.findById(t.getPaciente().getId());
            if(paciente.isPresent()){
                PacienteTurnoDTO pacienteTurnoDTO = new PacienteTurnoDTO(paciente.get().getId(), paciente.get().getNombre(), paciente.get().getApellido(), paciente.get().getDocumento());
                turnoOdontologoDTOS.add(new TurnoOdontologoDTO(t.getId(), t.getFecha(), pacienteTurnoDTO, t.getHora()));
            }
        }

        Page<TurnoOdontologoDTO> pageTurnos = new PageImpl<>(turnoOdontologoDTOS.stream().toList(), pageable, turnosOdontologo.getTotalPages());

        return new OdontologoDTO(odontologoDB.getId(), odontologoDB.getNombre(), odontologoDB.getApellido(), odontologoDB.getMatricula(), pageTurnos);
    }

}
