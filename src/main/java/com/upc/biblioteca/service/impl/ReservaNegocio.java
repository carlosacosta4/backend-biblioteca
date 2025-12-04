package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.dto.ReservaDto;
import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.entity.Prestamo;
import com.upc.biblioteca.entity.Reserva;
import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.enums.LibroEstado;
import com.upc.biblioteca.repository.ILibroRepositorio;
import com.upc.biblioteca.repository.IPrestamoRepositorio;
import com.upc.biblioteca.repository.IReservaRepositorio;
import com.upc.biblioteca.repository.IUsuarioRepositorio;
import com.upc.biblioteca.service.IReservaNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaNegocio implements IReservaNegocio {

    @Autowired
    private IReservaRepositorio reservaRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ILibroRepositorio libroRepositorio;

    @Autowired
    private IPrestamoRepositorio prestamoRepositorio;

    @Override
    public Reserva registrarReserva(String documentoIdentidad, String isbnLibro) throws Exception {
        Usuario usuario = usuarioRepositorio.findByDocumentoIdentidad(documentoIdentidad)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        Libro libro = libroRepositorio.findByIsbnLibro(isbnLibro)
                .orElseThrow(() -> new Exception("Libro no encontrado"));

        if (libro.getCantidadLibro() <= 0) {
            throw new Exception("No hay ejemplares disponibles de este libro");
        }

        // Validar si existe una reserva para el mismo libro y usuario
        List<Reserva> reservas = reservaRepositorio.findByUsuarioDocumento(documentoIdentidad);
        boolean existeReserva = reservas.stream()
                .anyMatch(r -> r.getLibro().getIsbnLibro().equals(isbnLibro));
        if (existeReserva) {
            throw new Exception("Ya existe una reserva para este libro y usuario");
        }

        List<Prestamo> prestamos = prestamoRepositorio.findByUsuarioDocumento(documentoIdentidad);
        boolean existePrestamo = prestamos.stream()
                .anyMatch(p -> p.getLibro().getIsbnLibro().equals(isbnLibro) &&
                        (p.getEstado() == LibroEstado.ACTIVO || p.getEstado() == LibroEstado.VENCIDO));
        if (existePrestamo) {
            throw new Exception("Ya existe un préstamo activo o vencido para este libro y usuario");
        }

        libro.setCantidadLibro(libro.getCantidadLibro() - 1);
        libroRepositorio.save(libro);

        Reserva reserva = Reserva.builder()
                .usuario(usuario)
                .libro(libro)
                .estado(LibroEstado.RESERVADO)
                .build();

        return reservaRepositorio.save(reserva);
    }



    @Override
    public List<ReservaDto> obtenerReservasPorDocumento(String documentoIdentidad) throws Exception {
        List<Reserva> reservas = reservaRepositorio.findByUsuarioDocumento(documentoIdentidad);
        return reservas.stream().map(reserva -> ReservaDto.builder()
                .idReserva(reserva.getIdReserva())
                .documentoUsuario(reserva.getUsuario().getDocumentoIdentidad())
                .isbnLibro(reserva.getLibro().getIsbnLibro())
                .estado(reserva.getEstado())
                .build()
        ).toList();
    }
}

