package com.manu.clinica.dental.Controller;

import com.manu.clinica.dental.Entity.Paciente;
import com.manu.clinica.dental.Exceptions.PacienteAlreadyExistsException;
import com.manu.clinica.dental.Exceptions.ResourceNotFoundException;
import com.manu.clinica.dental.Service.PacienteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/pacientes")
@CrossOrigin("*")
public class PacienteController {

    @Autowired
    private PacienteServiceImpl service;

    @PostMapping
    public ResponseEntity<?> crearPaciente(@RequestBody @Valid Paciente paciente) throws PacienteAlreadyExistsException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPaciente(paciente));
    }

    @PutMapping
    public ResponseEntity<?> actualizarPaciente(@RequestBody @Valid Paciente paciente) throws ResourceNotFoundException, PacienteAlreadyExistsException {
        service.actualizarPaciente(paciente);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPacientePorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.buscarPacientePorId(id).get());
    }

    @GetMapping(params = "email")
    public ResponseEntity<?> bucsarPacientePorCorreo(@RequestParam(name = "email") String email) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.buscarPacientePorCorreo(email).get());
    }

    @GetMapping(params = "documento")
    public ResponseEntity<?> buscarPacientePorDocumento(@RequestParam(name = "documento") String documento) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.buscarPacientePorDocumento(documento));
    }

    @GetMapping
    public ResponseEntity<?> listarTodosLosPacientes(){
        return ResponseEntity.ok(service.listarTodosLosPacientes());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        service.eliminarPaciente(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{nPagina}", params = "order")
    public ResponseEntity<?> ordenarPacientes(@PathVariable int nPagina, @RequestParam("order") String orden){
        return ResponseEntity.ok(service.ordenarPacientes(orden, nPagina));
    }

    @GetMapping("/pagina/{nPagina}")
    public ResponseEntity<?> listarPacientesPaginados(@PathVariable int nPagina){
        int cantidadPorPagina = 10;
        Pageable pageable = PageRequest.of(nPagina - 1, cantidadPorPagina);
        return ResponseEntity.ok(service.listarPacientesPaginados(pageable));
    }

    @GetMapping("/turnos/{id}/pagina/{pagina}")
    public ResponseEntity<?> listarTodosLosTurnosDelPaciente(@PathVariable Long id, @PathVariable int pagina){
        return ResponseEntity.ok(service.listarTodosLosTurnosDelPaciente(id, pagina));
    }

}
