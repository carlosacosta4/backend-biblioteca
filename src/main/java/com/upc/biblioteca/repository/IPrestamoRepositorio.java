package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPrestamoRepositorio extends JpaRepository<Prestamo, Long> {

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.documentoIdentidad = :documentoIdentidad")
    List<Prestamo> findByUsuarioDocumento(@Param("documentoIdentidad") String documentoIdentidad);

}

