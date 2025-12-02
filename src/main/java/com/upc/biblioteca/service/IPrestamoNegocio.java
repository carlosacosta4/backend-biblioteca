package com.upc.biblioteca.service;

import com.upc.biblioteca.entity.Prestamo;
import java.time.LocalDate;

public interface IPrestamoNegocio {
    Prestamo registrarPorDocumentoEIsbn(String documentoIdentidad, String isbnLibro,
                                        LocalDate fechaPrestamo, LocalDate fechaDevolucion) throws Exception;

}
