package com.manu.clinica.dental.Controller;

import com.manu.clinica.dental.Dto.TurnoRequestDTO;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Exceptions.TurnoAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.TurnoDateBeforeException;
import com.manu.clinica.dental.Service.TurnoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/turnos")
@CrossOrigin("*")
public class TurnoController {

    @Autowired
    private TurnoServiceImpl service;

    @PostMapping
    public ResponseEntity<?> crearTurno(@RequestBody @Valid TurnoRequestDTO turno) throws TurnoDateBeforeException, ResourceNotFoundException, TurnoAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearTurno(turno));
    }

    @GetMapping
    public ResponseEntity<?> listarTodosLosTurnos(){
        return ResponseEntity.ok(service.listarTodosLosTurnos());
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<?> listarTurnosPaginados(@PathVariable int pagina){
        return ResponseEntity.ok(service.listarTurnosPaginados(pagina));
    }

    @GetMapping(path = "/{nPagina}", params = "order")
    public ResponseEntity<?> ordenarTurnos(@PathVariable int nPagina, @RequestParam("order") String orden){
        return ResponseEntity.ok(service.listarTurnosOdenados(orden, nPagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.buscarTurnoPorId(id));
    }

    @PutMapping
    public ResponseEntity<?> actualizarTurno(@RequestBody @Valid TurnoRequestDTO turnoRequestDTO) throws TurnoDateBeforeException, ResourceNotFoundException, TurnoAlreadyExistsException {
        service.actualizarTurno(turnoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        service.eliminarTurno(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fecha/{fecha}/{pagina}")
    public ResponseEntity<?> obtenerTurnosPorFecha(@PathVariable("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") String fechaStr, @PathVariable int pagina) {
        LocalDate fecha = LocalDate.parse(fechaStr);
        return ResponseEntity.ok(service.listarTurnosPorFechaPaginados(pagina, fecha));
    }

    @GetMapping("/documento/{documento}/{pagina}")
    public ResponseEntity<?> listarTurnosPaginadosPorPaciente(@PathVariable String documento, @PathVariable int pagina){
        return ResponseEntity.ok(service.listarTurnosPorPaciente(pagina, documento));
    }

    @GetMapping("/matricula/{matricula}/{pagina}")
    public ResponseEntity<?> listarTurnosPaginadosPorOdontologo(@PathVariable String matricula, @PathVariable int pagina){
        return ResponseEntity.ok(service.listarTurnosPorOdontologo(pagina, matricula));
    }


    // hacer getmapping de matricula

}
