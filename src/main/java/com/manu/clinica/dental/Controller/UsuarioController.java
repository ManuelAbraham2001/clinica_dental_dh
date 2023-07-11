package com.manu.clinica.dental.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.manu.clinica.dental.Entity.Usuario;
import com.manu.clinica.dental.Security.JwtUtil;
import com.manu.clinica.dental.Service.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioServiceImpl service;

    @Autowired
    private JwtUtil jwtUtil;

//    @PostMapping("/register")
//    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid Usuario usuario){
//        return ResponseEntity.ok(service.registrar(usuario));
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generarToken(authentication);
        Map<String, String> response = new HashMap<>();
        response.put("type", "Bearer");
        response.put("token", token);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarToken(@RequestHeader(value = "Authorization") String token){
        String tokenSubstring = token.substring(7);
        return ResponseEntity.ok(jwtUtil.esValido(tokenSubstring));
    }


}
