package com.upc.biblioteca.service;

import com.upc.biblioteca.dto.LoginResponseDto;
import com.upc.biblioteca.entity.Usuario;

import java.util.List;

public interface IUsuarioNegocio {

    public List<Usuario> listar();
    public Usuario registrar(Usuario usuario);
    Usuario buscarPorDocumentoIdentidad(String documentoIdentidad);
    public LoginResponseDto login(String correo, String password);
}
