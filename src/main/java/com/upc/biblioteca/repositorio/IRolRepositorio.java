package com.upc.biblioteca.repositorio;

import com.upc.biblioteca.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepositorio extends JpaRepository<Rol, Long> {
}
