package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
