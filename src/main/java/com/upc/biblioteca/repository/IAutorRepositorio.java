package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAutorRepositorio extends JpaRepository<Autor, Long> {



}
