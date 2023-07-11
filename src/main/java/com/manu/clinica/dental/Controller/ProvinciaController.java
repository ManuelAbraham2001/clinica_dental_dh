package com.manu.clinica.dental.Controller;

import com.manu.clinica.dental.Service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provincias")
@CrossOrigin("*")
public class ProvinciaController {

    @Autowired
    private ProvinciaService service;

    @GetMapping
    public ResponseEntity<?> listarTodasLasProvincias(){
        return ResponseEntity.ok(service.listarTodasLasProvincias());
    }

}
