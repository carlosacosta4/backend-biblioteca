package com.upc.biblioteca.negocio;

import com.upc.biblioteca.entidades.Rol;
import com.upc.biblioteca.entidades.Usuario;
import com.upc.biblioteca.repositorio.IRolRepositorio;
import com.upc.biblioteca.repositorio.IUsuarioRepositorio;
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

}
