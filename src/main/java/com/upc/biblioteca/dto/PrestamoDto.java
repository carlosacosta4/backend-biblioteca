package com.upc.biblioteca.dto;

import com.upc.biblioteca.enums.LibroEstado;
import java.time.LocalDate;

public record PrestamoDto(
    Long idPrestamo,
    String documentoUsuario,
    String tituloLibro,
    LocalDate fechaPrestamo,
    LocalDate fechaDevolucion,
    LibroEstado estado
) {}

