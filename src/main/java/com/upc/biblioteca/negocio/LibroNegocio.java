package com.upc.biblioteca.negocio;

import com.upc.biblioteca.entidades.Libro;
import com.upc.biblioteca.repositorio.ILibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroNegocio implements ILibroNegocio {

    @Autowired
    ILibroRepositorio repositorio;

    @Override
    public List<Libro> listar() {
        return repositorio.findAll();
    }

    @Override
    public Libro registrar(Libro libro) {
        return repositorio.save(libro);
    }
}
