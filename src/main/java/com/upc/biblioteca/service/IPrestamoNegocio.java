package com.upc.biblioteca.service;

import com.upc.biblioteca.dto.PrestamoDto;
import com.upc.biblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;

public interface IPrestamoNegocio {
    Prestamo registrarPorDocumentoEIsbn(String documentoIdentidad, String isbnLibro,
                                        LocalDate fechaPrestamo, LocalDate fechaDevolucion) throws Exception;

    List<PrestamoDto> obtenerPrestamosPorDocumento(String documentoIdentidad) throws Exception;

}
