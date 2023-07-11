package com.manu.clinica.dental;

import com.manu.clinica.dental.Entity.Provincia;
import com.manu.clinica.dental.Entity.Usuario;
import com.manu.clinica.dental.Repository.ProvinciaRepository;
import com.manu.clinica.dental.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        String[] provincias = {"Buenos Aires", "Catamarca", "Chaco", "Chubut", "Córdoba", "Corrientes", "Entre rios", "Formosa", "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquen", "Rio negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe", "Santiago del Estero", "Tierra del Fuego", "Tucuman"};

        for (String provincia : provincias) {
            provinciaRepository.save(new Provincia(provincia));
        }

        List<String> rolesAdmin = new ArrayList<>();
        List<String> rolesUser = new ArrayList<>();
        rolesAdmin.add("ROLE_ADMIN");
        rolesUser.add("ROLE_USER");

        Usuario admin = new Usuario("admin", passwordEncoder.encode("admin"), true, rolesAdmin);
        Usuario user = new Usuario("user", passwordEncoder.encode("user"), false, rolesUser);

        usuarioRepository.save(admin);
        usuarioRepository.save(user);

    }
}
