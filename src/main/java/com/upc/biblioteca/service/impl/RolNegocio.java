package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.entity.Rol;
import com.upc.biblioteca.repository.IRolRepositorio;
import com.upc.biblioteca.service.IRolNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolNegocio implements IRolNegocio {
    @Autowired
    IRolRepositorio repositorio;
    @Override
    public List<Rol> listar(){
        return repositorio.findAll().stream().toList();
    }
}
