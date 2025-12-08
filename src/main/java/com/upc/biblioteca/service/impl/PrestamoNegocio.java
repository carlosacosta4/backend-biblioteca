package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.dto.PrestamoDto;
import com.upc.biblioteca.entity.Reserva;
import com.upc.biblioteca.enums.LibroEstado;
import com.upc.biblioteca.repository.IReservaRepositorio;
import com.upc.biblioteca.service.IPrestamoNegocio;
import org.springframework.stereotype.Service;

import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.entity.Prestamo;
import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.repository.ILibroRepositorio;
import com.upc.biblioteca.repository.IPrestamoRepositorio;
import com.upc.biblioteca.repository.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoNegocio implements IPrestamoNegocio {

    @Autowired
    private IPrestamoRepositorio prestamoRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IReservaRepositorio reservaRepositorio;

    @Autowired
    private ILibroRepositorio libroRepositorio;

    @Override
    public Prestamo registrarPorDocumentoEIsbn(String documentoIdentidad, String isbnLibro,
                                               LocalDate fechaPrestamo, LocalDate fechaDevolucion) throws Exception {
        Usuario usuario = usuarioRepositorio.findByDocumentoIdentidad(documentoIdentidad)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        Libro libro = libroRepositorio.findByIsbnLibro(isbnLibro)
                .orElseThrow(() -> new Exception("Libro no encontrado"));

        if (libro.getCantidadLibro() <= 0) {
            throw new Exception("No hay ejemplares disponibles de este libro");
        }

        List<Prestamo> prestamos = prestamoRepositorio.findByUsuarioDocumento(documentoIdentidad);
        boolean existePrestamo = prestamos.stream()
                .anyMatch(p -> p.getLibro().getIsbnLibro().equals(isbnLibro) &&
                        (p.getEstado() == LibroEstado.ACTIVO || p.getEstado() == LibroEstado.VENCIDO));
        if (existePrestamo) {
            throw new Exception("Ya existe un préstamo activo o vencido para este libro y usuario");
        }

        List<Reserva> reservas = reservaRepositorio.findByUsuarioDocumento(documentoIdentidad);
        boolean existeReserva = reservas.stream()
                .anyMatch(r -> r.getLibro().getIsbnLibro().equals(isbnLibro));
        if (existeReserva) {
            throw new Exception("Ya existe una reserva para este libro y usuario");
        }

        libro.setCantidadLibro(libro.getCantidadLibro() - 1);
        libroRepositorio.save(libro);

        Prestamo prestamo = Prestamo.builder()
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(fechaPrestamo)
                .fechaDevolucion(fechaDevolucion)
                .estado(LibroEstado.ACTIVO)
                .build();

        return prestamoRepositorio.save(prestamo);
    }



    @Override
    public List<PrestamoDto> obtenerPrestamosPorDocumento(String documentoIdentidad) throws Exception {
        List<Prestamo> prestamos = prestamoRepositorio.findByUsuarioDocumento(documentoIdentidad);
        return prestamos.stream().map(prestamo -> new PrestamoDto(
                prestamo.getIdPrestamo(),
                prestamo.getUsuario().getDocumentoIdentidad(),
                prestamo.getLibro().getTituloLibro(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucion(),
                prestamo.getEstado()
        )).toList();
    }

    @Override
    public void actualizarEstadoPrestamos() {
        List<Prestamo> prestamos = prestamoRepositorio.findAll();
        prestamos.forEach(prestamo -> {
            if (prestamo.getEstado() == LibroEstado.ACTIVO && prestamo.getFechaDevolucion().isBefore(LocalDate.now())) {
                prestamo.setEstado(LibroEstado.VENCIDO);
                prestamoRepositorio.save(prestamo);
            }
        });
    }

    @Override
    public void devolverPrestamo(String documentoIdentidad, String isbnLibro) throws Exception {
        List<Prestamo> prestamos = prestamoRepositorio.findByUsuarioDocumento(documentoIdentidad);
        Prestamo prestamo = prestamos.stream()
                .filter(p -> p.getLibro().getIsbnLibro().equals(isbnLibro) &&
                        (p.getEstado() == LibroEstado.ACTIVO || p.getEstado() == LibroEstado.VENCIDO))
                .findFirst()
                .orElseThrow(() -> new Exception("No existe préstamo activo o vencido para ese usuario y libro"));

        prestamo.setEstado(LibroEstado.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setCantidadLibro(libro.getCantidadLibro() + 1);
        libroRepositorio.save(libro);

        prestamoRepositorio.save(prestamo);
    }

}

