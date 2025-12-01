package com.upc.biblioteca.negocio;

import com.upc.biblioteca.entidades.Libro;

import java.util.List;

public interface ILibroNegocio {

    public List<Libro> listar();
    public Libro registrar(Libro libro);

}
