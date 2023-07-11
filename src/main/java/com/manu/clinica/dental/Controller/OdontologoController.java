package com.manu.clinica.dental.Controller;

import com.manu.clinica.dental.Entity.Odontologo;
import com.manu.clinica.dental.Exceptions.OdontologoAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Service.OdontologoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/odontologos")
@CrossOrigin("*")
public class OdontologoController {

    @Autowired
    private OdontologoServiceImpl service;

    @PostMapping
    public ResponseEntity<?> crearOdontologo(@RequestBody @Valid Odontologo odontologo) throws OdontologoAlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<?> actualizarOdontologo(@RequestBody @Valid Odontologo odontologo) throws ResourceNotFoundException, OdontologoAlreadyExistsException {
        service.actualizarOdontologo(odontologo);
        return ResponseEntity.ok().body(odontologo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOdontologoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.buscarOdontologoPorId(id));
    }

    @GetMapping(params = "matricula")
    public ResponseEntity<?> buscarOdontologoPorMatricula(@RequestParam("matricula") String matricula) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.buscarOdontologoPorMatricula(matricula));
    }

    @GetMapping
    public ResponseEntity<?> listarTodosLosOdontologos(){
        return ResponseEntity.ok(service.listarTodosLosOdontologos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        service.eliminarOdontologo(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagina/{nPagina}")
    public ResponseEntity<?> listarOdontologosPorPagina(@PathVariable int nPagina){
        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(nPagina - 1, cantidadPorPagina);
        return ResponseEntity.ok(service.listarOdontologosPorPagina(pageable));
    }

    @GetMapping("/{id}/turnos/pagina/{nPagina}")
    public ResponseEntity<?> listarTurnosPorOdontologo(@PathVariable Long id, @PathVariable int nPagina) throws ResourceNotFoundException {
        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(nPagina - 1, cantidadPorPagina);
        return ResponseEntity.ok(service.listarTurnosPorOdontologo(id, pageable));
    }

    @GetMapping(path = "ordenar/{nPagina}", params = "order")
    public ResponseEntity<?> ordenarOdontologos(@PathVariable int nPagina, @RequestParam("order") String orden){
        return ResponseEntity.ok(service.ordenarOdontologos(orden, nPagina));
    }

}
