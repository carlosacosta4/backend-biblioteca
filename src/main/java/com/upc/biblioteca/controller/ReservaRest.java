// src/main/java/com/upc/biblioteca/controller/ReservaRest.java
package com.upc.biblioteca.controller;

import com.upc.biblioteca.dto.ErrorDto;
import com.upc.biblioteca.dto.ReservaRequestDto;
import com.upc.biblioteca.entity.Reserva;
import com.upc.biblioteca.service.IReservaNegocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ReservaRest {

    @Autowired
    private IReservaNegocio reservaNegocio;

    @PostMapping("/reserva")
    public ResponseEntity<?> crearReserva(@RequestBody ReservaRequestDto request) {
        try {
            Reserva saved = reservaNegocio.registrarReserva(
                    request.getDocumentoIdentidad(),
                    request.getIsbnLibro()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            ErrorDto error = new ErrorDto("Error al crear la reserva", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
