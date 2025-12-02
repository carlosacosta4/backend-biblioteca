package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.entidades.Libro;

import java.util.List;

public interface ILibroNegocio {

    public List<Libro> listar();
    public Libro registrar(Libro libro);

}
