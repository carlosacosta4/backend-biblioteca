package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.entity.Usuario;

import java.util.List;

public interface IUsuarioNegocio {

    public List<Usuario> listar();
    public Usuario registrar(Usuario usuario);
    Usuario buscarPorDocumentoIdentidad(String documentoIdentidad);
}
