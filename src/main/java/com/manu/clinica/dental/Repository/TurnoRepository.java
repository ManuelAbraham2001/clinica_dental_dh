package com.manu.clinica.dental.Repository;

import com.manu.clinica.dental.Entity.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findAllByFecha(LocalDate fecha);
    @Query("SELECT t FROM Turno t")
    Page<Turno> turnosPaginados(Pageable pageable);
    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha")
    Page<Turno> turnosPaginadosPorFecha(Pageable pageable, LocalDate fecha);
    @Query("SELECT t FROM Turno t JOIN t.paciente p WHERE p.documento = :documento")
    Page<Turno> turnosPaginadosPorPaciente(Pageable pageable, String documento);
    @Query("SELECT t FROM Turno t JOIN t.odontologo o WHERE o.matricula = :matricula")
    Page<Turno> turnosPaginadosPorOdontologo(Pageable pageable, String matricula);
    @Query("SELECT t FROM Turno t JOIN t.odontologo o WHERE o.id = :id")
    Page<Turno> listarTurnosPorOdontologo(Long id, Pageable pageable);
    @Query("SELECT t FROM Turno t WHERE t.paciente.id = :id")
    Page<Turno> listarTurnosDelPacientePaginados(Long id, Pageable pageable);

}
