package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.repository.IUsuarioRepositorio;
import com.upc.biblioteca.service.IUsuarioNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioNegocio implements IUsuarioNegocio {

    @Autowired
    IUsuarioRepositorio repositorio;
    @Override
    public List<Usuario> listar(){
        return repositorio.findAll().stream().toList();
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        return repositorio.save(usuario);
    }

    @Override
    public Usuario buscarPorDocumentoIdentidad(String documentoIdentidad) {
        return repositorio.findByDocumentoIdentidad(documentoIdentidad).orElse(null);
    }
}
