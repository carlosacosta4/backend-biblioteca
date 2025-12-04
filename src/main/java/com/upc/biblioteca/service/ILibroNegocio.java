package com.upc.biblioteca.service;

import com.upc.biblioteca.entity.Libro;

import java.util.List;

public interface ILibroNegocio {

    public List<Libro> listar();
    public Libro registrar(Libro libro);

    Libro buscarPorIsbn(String isbnLibro);
    List<Libro> buscarPorTitulo(String titulo);
}
