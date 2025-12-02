package com.upc.biblioteca.repositorio;

import com.upc.biblioteca.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByCorreoElectronico(String correoElectronico);

}
