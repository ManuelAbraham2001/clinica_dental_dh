package com.manu.clinica.dental.Exceptions;

public class PacienteAlreadyExistsException extends Exception{
    public PacienteAlreadyExistsException(String message) {
        super(message);
    }
}
