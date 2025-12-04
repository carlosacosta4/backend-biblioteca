package com.upc.biblioteca.repository;

import com.upc.biblioteca.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReservaRepositorio extends JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r WHERE r.usuario.documentoIdentidad = :documentoIdentidad")
    List<Reserva> findByUsuarioDocumento(@Param("documentoIdentidad") String documentoIdentidad);
}

