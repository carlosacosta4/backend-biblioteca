package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibroRepositorio extends JpaRepository<Libro, Long> {



}
