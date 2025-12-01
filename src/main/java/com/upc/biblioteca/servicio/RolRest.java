package com.upc.biblioteca.servicio;

import com.upc.biblioteca.entidades.Rol;
import com.upc.biblioteca.negocio.IRolNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolRest {
    @Autowired
    public IRolNegocio lbRolNegocio;

    @GetMapping("/roles")
    public List<Rol> obtenerRoles() {
        return lbRolNegocio.listar();
    }

}
