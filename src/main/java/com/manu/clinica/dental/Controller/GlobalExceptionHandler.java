package com.manu.clinica.dental.Controller;

import com.manu.clinica.dental.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@CrossOrigin("*")
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<?> odontologoNotFound(ResourceNotFoundException e, WebRequest request) {
        return new ResponseEntity<>("esta exception viene del global handler " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OdontologoAlreadyExistsException.class)
    private ResponseEntity<?> odontologoAlreadyExists(OdontologoAlreadyExistsException e, WebRequest request) {
        return new ResponseEntity<>("esta exception viene del global handler " + e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PacienteAlreadyExistsException.class)
    private ResponseEntity<?> pacienteAlreadyExists(PacienteAlreadyExistsException e, WebRequest request) {
        return new ResponseEntity<>("esta exception viene del global handler " + e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TurnoAlreadyExistsException.class)
    private ResponseEntity<?> turnoAlreadyExists(TurnoAlreadyExistsException e, WebRequest request) {
        return new ResponseEntity<>("esta exception viene del global handler " + e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TurnoDateBeforeException.class)
    private ResponseEntity<?> turnoDateBeforeNotFound(TurnoDateBeforeException e, WebRequest request) {
        return new ResponseEntity<>("esta exception viene del global handler " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> camposNulos(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
