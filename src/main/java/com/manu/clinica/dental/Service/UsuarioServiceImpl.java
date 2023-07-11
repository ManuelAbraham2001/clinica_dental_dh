package com.manu.clinica.dental.Service;

import com.manu.clinica.dental.Entity.Usuario;
import com.manu.clinica.dental.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrar(Usuario usuario) {

        if(usuario.getEsAdmin()){
            usuario.getRoles().add("ROLE_ADMIN");
        } else if (!usuario.getEsAdmin() || usuario.getEsAdmin() == null) {
            usuario.getRoles().add("ROLE_USER");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repo.save(usuario);
    }
}
