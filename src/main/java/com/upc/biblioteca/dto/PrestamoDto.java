package com.upc.biblioteca.dto;

import com.upc.biblioteca.enums.PrestamoEstado;
import java.time.LocalDate;

public record PrestamoDto(
    Long idPrestamo,
    String documentoUsuario,
    String tituloLibro,
    LocalDate fechaPrestamo,
    LocalDate fechaDevolucion,
    PrestamoEstado estado
) {}

