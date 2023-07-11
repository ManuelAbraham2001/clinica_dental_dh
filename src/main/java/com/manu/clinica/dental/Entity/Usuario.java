package com.manu.clinica.dental.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El username no puede ser null")
    @NotBlank
    private String username;
    @NotNull(message = "La password no puede ser null")
    @NotBlank
    private String password;
    @NotNull(message = "El esAdmin no puede ser null")
    private Boolean esAdmin;
    private List<String> roles = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String username, String password, Boolean esAdmin, List<String> roles) {
        this.username = username;
        this.password = password;
        this.esAdmin = esAdmin;
        this.roles = roles;
    }
}
