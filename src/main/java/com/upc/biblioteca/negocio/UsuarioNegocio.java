package com.upc.biblioteca.negocio;

import com.upc.biblioteca.dto.Header;
import com.upc.biblioteca.dto.LoginResponseDto;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<Usuario> listar(){
        return repositorio.findAll().stream().toList();
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        return repositorio.save(usuario);
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
        loginResponseDto.setRol(usuario.getRol());


        return loginResponseDto;
    }

}
