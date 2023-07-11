package com.manu.clinica.dental.Service;

import com.manu.clinica.dental.Entity.Provincia;
import com.manu.clinica.dental.Repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaServiceImpl implements ProvinciaService{

    @Autowired
    private ProvinciaRepository repo;

    @Override
    public List<Provincia> listarTodasLasProvincias() {
        return repo.findAll();
    }
}
