package com.manu.clinica.dental.Repository;

import com.manu.clinica.dental.Entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByEmail(String email);
    Optional<Paciente> findByDocumento(String documento);
    @Query("SELECT p FROM Paciente p")
    Page<Paciente> listarPacientesPaginados(Pageable pageable);


}
