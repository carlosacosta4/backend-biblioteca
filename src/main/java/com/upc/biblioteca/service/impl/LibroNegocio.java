package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.repository.ILibroRepositorio;
import com.upc.biblioteca.service.ILibroNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Libro existente = repositorio.findByIsbnLibro(libro.getIsbnLibro()).orElse(null);

        if (existente != null) {
            throw new RuntimeException("El ISBN ya existe");
        }

        return repositorio.save(libro);
    }

    @Override
    public Libro buscarPorIsbn(String isbnLibro) {
        return repositorio.findByIsbnLibro(isbnLibro).orElse(null);
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        return repositorio.findByTituloLibroContainingIgnoreCase(titulo); // Implementación
    }
}
