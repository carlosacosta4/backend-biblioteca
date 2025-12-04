package com.upc.biblioteca.service;

import com.upc.biblioteca.dto.ReservaDto;
import com.upc.biblioteca.entity.Reserva;

import java.util.List;

public interface IReservaNegocio {
    Reserva registrarReserva(String documentoIdentidad, String isbnLibro) throws Exception;
    List<ReservaDto> obtenerReservasPorDocumento(String documentoIdentidad) throws Exception;
}
