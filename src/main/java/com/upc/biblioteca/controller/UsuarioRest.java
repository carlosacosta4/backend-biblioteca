package com.upc.biblioteca.controller;

import com.upc.biblioteca.dto.ErrorDto;
import com.upc.biblioteca.dto.LoginRequestDto;
import com.upc.biblioteca.dto.LoginResponseDto;
import com.upc.biblioteca.dto.UsuarioDto;
import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.service.IUsuarioNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/usuarios/buscar")
    public ResponseEntity<?> buscarUsuarios(@RequestParam String termino) {
        List<Usuario> usuarios = lbUsuarioNegocio.buscarUsuarios(termino);
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDto("Usuario no encontrado", "No se encontraron usuarios con el término proporcionado."));
        }
        List<UsuarioDto> dtos = usuarios.stream()
                .map(u -> new UsuarioDto(u.getDocumentoIdentidad(), u.getNombres(), u.getApellidos()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {

        LoginResponseDto res = lbUsuarioNegocio.login(request.getCorreoElectronico(), request.getPassword());

        return res;
    }

}
