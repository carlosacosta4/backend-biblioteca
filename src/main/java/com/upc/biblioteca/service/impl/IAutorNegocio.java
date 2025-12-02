package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.entidades.Autor;

import java.util.List;

public interface IAutorNegocio {

    public List<Autor> listar();
    public Autor registrar(Autor autor);

}
