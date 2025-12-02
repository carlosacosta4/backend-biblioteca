package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepositorio extends JpaRepository<Rol, Long> {
}
