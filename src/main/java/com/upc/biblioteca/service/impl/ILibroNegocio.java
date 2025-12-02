package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.entity.Libro;

import java.util.List;

public interface ILibroNegocio {

    public List<Libro> listar();
    public Libro registrar(Libro libro);
    Libro buscarPorIsbn(String isbnLibro);
}
