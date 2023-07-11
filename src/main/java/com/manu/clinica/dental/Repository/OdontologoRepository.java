package com.manu.clinica.dental.Repository;

import com.manu.clinica.dental.Entity.Odontologo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    Optional<Odontologo> findByMatricula(String matricula);
    @Query("SELECT o FROM Odontologo o")
    Page<Odontologo> listarOdonotlogosPorPagina(Pageable pageable);

}
