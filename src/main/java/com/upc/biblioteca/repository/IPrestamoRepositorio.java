package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Prestamo;
import com.upc.biblioteca.enums.PrestamoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IPrestamoRepositorio extends JpaRepository<Prestamo, Long> {

}

