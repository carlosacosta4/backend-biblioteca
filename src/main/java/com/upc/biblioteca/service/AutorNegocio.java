package com.upc.biblioteca.service;

import com.upc.biblioteca.entity.Autor;
import com.upc.biblioteca.repository.IAutorRepositorio;
import com.upc.biblioteca.service.impl.IAutorNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorNegocio implements IAutorNegocio {

    @Autowired
    IAutorRepositorio repositorio;

    @Override
    public List<Autor> listar() {
        return repositorio.findAll().stream().toList();
    }

    @Override
    public Autor registrar(Autor autor) {
        return repositorio.save(autor);
    }
}
