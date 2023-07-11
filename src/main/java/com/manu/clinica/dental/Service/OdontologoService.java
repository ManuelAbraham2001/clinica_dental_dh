package com.manu.clinica.dental.Service;

import com.manu.clinica.dental.Dto.OdontologoDTO;
import com.manu.clinica.dental.Entity.Odontologo;
import com.manu.clinica.dental.Exceptions.OdontologoAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OdontologoService {
    Odontologo crearOdontologo(Odontologo odontologo) throws OdontologoAlreadyExistsException, ResourceNotFoundException;
    Odontologo buscarOdontologoPorId(Long id) throws ResourceNotFoundException;
    Odontologo buscarOdontologoPorMatricula(String matricula) throws ResourceNotFoundException;
    void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException, OdontologoAlreadyExistsException;
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
    List<Odontologo> listarTodosLosOdontologos();
    Page<Odontologo> listarOdontologosPorPagina(Pageable pageable);
    Page<Odontologo> ordenarOdontologos(String orden, int nPagina);
    OdontologoDTO listarTurnosPorOdontologo(Long id, Pageable pageable) throws ResourceNotFoundException;

}
