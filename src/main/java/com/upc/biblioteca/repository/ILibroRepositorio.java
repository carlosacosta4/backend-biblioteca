package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ILibroRepositorio extends JpaRepository<Libro, Long> {
    Optional<Libro> findByIsbnLibro(String isbnLibro);
    List<Libro> findByTituloLibroContainingIgnoreCase(String titulo);
}

