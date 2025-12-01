package com.upc.biblioteca.negocio;

import com.upc.biblioteca.entidades.Rol;
import com.upc.biblioteca.repositorio.IRolRepositorio;
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
