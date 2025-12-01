package com.upc.biblioteca.repositorio;

import com.upc.biblioteca.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAutorRepositorio extends JpaRepository<Autor, Long> {



}
