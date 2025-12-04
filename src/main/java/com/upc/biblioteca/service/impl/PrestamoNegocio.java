package com.upc.biblioteca.service.impl;

import com.upc.biblioteca.dto.PrestamoDto;
import com.upc.biblioteca.enums.PrestamoEstado;
import com.upc.biblioteca.service.IPrestamoNegocio;
import org.springframework.stereotype.Service;

import com.upc.biblioteca.entity.Libro;
import com.upc.biblioteca.entity.Prestamo;
import com.upc.biblioteca.entity.Usuario;
import com.upc.biblioteca.repository.ILibroRepositorio;
import com.upc.biblioteca.repository.IPrestamoRepositorio;
import com.upc.biblioteca.repository.IUsuarioRepositorio;
import com.upc.biblioteca.service.IPrestamoNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoNegocio implements IPrestamoNegocio {

    @Autowired
    private IPrestamoRepositorio prestamoRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ILibroRepositorio libroRepositorio;

    // src/main/java/com/upc/biblioteca/service/impl/PrestamoNegocio.java
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
                        (p.getEstado() == PrestamoEstado.ACTIVO || p.getEstado() == PrestamoEstado.VENCIDO));
        if (existePrestamo) {
            throw new Exception("Ya existe un préstamo activo o vencido para este libro y usuario");
        }

        libro.setCantidadLibro(libro.getCantidadLibro() - 1);
        libroRepositorio.save(libro);

        Prestamo prestamo = Prestamo.builder()
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(fechaPrestamo)
                .fechaDevolucion(fechaDevolucion)
                .estado(PrestamoEstado.ACTIVO)
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
            if (prestamo.getEstado() == PrestamoEstado.ACTIVO && prestamo.getFechaDevolucion().isBefore(LocalDate.now())) {
                prestamo.setEstado(PrestamoEstado.VENCIDO);
                prestamoRepositorio.save(prestamo);
            }
        });
    }

    @Override
    public void devolverPrestamo(String documentoIdentidad, String isbnLibro) throws Exception {
        List<Prestamo> prestamos = prestamoRepositorio.findByUsuarioDocumento(documentoIdentidad);
        Prestamo prestamo = prestamos.stream()
                .filter(p -> p.getLibro().getIsbnLibro().equals(isbnLibro) &&
                        (p.getEstado() == PrestamoEstado.ACTIVO || p.getEstado() == PrestamoEstado.VENCIDO))
                .findFirst()
                .orElseThrow(() -> new Exception("No existe préstamo activo o vencido para ese usuario y libro"));

        prestamo.setEstado(PrestamoEstado.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setCantidadLibro(libro.getCantidadLibro() + 1);
        libroRepositorio.save(libro);

        prestamoRepositorio.save(prestamo);
    }

}

