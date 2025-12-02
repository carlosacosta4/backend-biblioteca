package com.upc.biblioteca.controller;

import com.upc.biblioteca.entidades.Usuario;
import com.upc.biblioteca.service.impl.IUsuarioNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    public IUsuarioNegocio lbUsuarioNegocio;

    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        return lbUsuarioNegocio.listar();
    }

    @PostMapping("/usuario")
    public void crearLibro(@RequestBody Usuario usuario) {
        Usuario lb;
        try{
            lb = lbUsuarioNegocio.registrar(usuario);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
