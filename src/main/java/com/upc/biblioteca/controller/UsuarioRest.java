package com.upc.biblioteca.controller;

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

    @GetMapping("/usuario/documento/{documentoIdentidad}")
    public ResponseEntity<UsuarioDto> obtenerPorDocumento(@PathVariable String documentoIdentidad) {
        try {
            Usuario usuario = lbUsuarioNegocio.buscarPorDocumentoIdentidad(documentoIdentidad);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            UsuarioDto dto = new UsuarioDto(usuario.getDocumentoIdentidad(), usuario.getNombres());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {

        LoginResponseDto res = lbUsuarioNegocio.login(request.getCorreoElectronico(), request.getPassword());

        return res;
    }

}
