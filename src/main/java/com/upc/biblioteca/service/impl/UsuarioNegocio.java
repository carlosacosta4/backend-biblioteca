package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.dto.LoginResponseDto;
import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.repository.IUsuarioRepositorio;
import com.upc.biblioteca.service.IUsuarioNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upc.biblioteca.dto.Header;

import java.util.List;

@Service
public class UsuarioNegocio implements IUsuarioNegocio {

    @Autowired
    IUsuarioRepositorio repositorio;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public List<Usuario> listar(){
        return repositorio.findAll().stream().toList();
    }

    @Override
    public Usuario registrar(Usuario usuario) {

        Usuario existenteDocumentoIdentidad = repositorio.findByDocumentoIdentidad(usuario.getDocumentoIdentidad()).orElse(null);
        Usuario existenteCorreo = repositorio.findByCorreoElectronico(usuario.getCorreoElectronico());

        if (existenteDocumentoIdentidad != null) {
            throw new RuntimeException("El documento de identidad ya existe");
        }

        if (existenteCorreo != null) {
            throw new RuntimeException("El correo electrónico ya existe");
        }

        return repositorio.save(usuario);
    }

    @Override
    public Usuario buscarPorDocumentoIdentidad(String documentoIdentidad) {
        return repositorio.findByDocumentoIdentidad(documentoIdentidad).orElse(null);
    }

    @Override
    public List<Usuario> buscarUsuarios(String termino) {
        return repositorio.findByNombresContainingIgnoreCaseOrCorreoElectronicoContainingIgnoreCase(termino, termino);
    }

    @Override
    public LoginResponseDto login(String correo, String password) {

        Header encabezado = new Header();
        LoginResponseDto  loginResponseDto = new LoginResponseDto();
        Usuario usuario = repositorio.findByCorreoElectronico(correo);

        if (usuario == null || !usuario.getContrasenia().equals(password)) {

            encabezado.setResultado(false);
            encabezado.setMensaje("Credenciales no coinciden");

            loginResponseDto.setEncabezado(encabezado);

            return loginResponseDto;
        }

        String token = jwtUtil.generateToken(usuario.getCorreoElectronico());

        encabezado.setResultado(true);
        encabezado.setMensaje("Búsqueda exitosa");

        loginResponseDto.setEncabezado(encabezado);
        loginResponseDto.setToken(token);
        loginResponseDto.setNombres(usuario.getNombres());
        loginResponseDto.setApellidos(usuario.getApellidos());
        loginResponseDto.setDocumentoIdentidad(usuario.getDocumentoIdentidad());
        loginResponseDto.setRol(usuario.getRol());


        return loginResponseDto;
    }
}
