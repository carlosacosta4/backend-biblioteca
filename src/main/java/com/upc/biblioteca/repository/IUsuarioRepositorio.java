package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDocumentoIdentidad(String documentoIdentidad);
    List<Usuario> findByNombresContainingIgnoreCaseOrCorreoElectronicoContainingIgnoreCase(String nombres, String correo);
	Usuario findByCorreoElectronico(String correoElectronico);
}