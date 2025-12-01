package com.upc.biblioteca.repositorio;

import com.upc.biblioteca.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibroRepositorio extends JpaRepository<Libro, Long> {



}
