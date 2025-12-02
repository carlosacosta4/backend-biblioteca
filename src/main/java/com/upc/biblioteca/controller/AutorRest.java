package com.upc.biblioteca.controller;

import com.upc.biblioteca.entity.Autor;
import com.upc.biblioteca.service.IAutorNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AutorRest {

    @Autowired
    public IAutorNegocio lbAutorNegocio;

    @GetMapping("/autores")
    public List<Autor> obtenerAutores() {
        return lbAutorNegocio.listar();
    }

    @PostMapping("/autor")
    public void crearAutor(@RequestBody Autor autor) {
        Autor lb;
        try{
            lb = lbAutorNegocio.registrar(autor);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
