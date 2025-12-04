package com.upc.biblioteca.controller;

import com.upc.biblioteca.dto.PrestamoDto;
import org.springframework.web.bind.annotation.*;

import com.upc.biblioteca.dto.PrestamoRequestDto;
import com.upc.biblioteca.entity.Prestamo;
import com.upc.biblioteca.service.IPrestamoNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrestamoRest {

    @Autowired
    private IPrestamoNegocio prestamoNegocio;

    @PostMapping("/prestamo")
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequestDto request) {
        try {
            Prestamo saved = prestamoNegocio.registrarPorDocumentoEIsbn(
                    request.getDocumentoIdentidad(),
                    request.getIsbnLibro(),
                    request.getFechaPrestamo(),
                    request.getFechaDevolucion()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/prestamo/usuario/{documentoIdentidad}")
    public ResponseEntity<?> obtenerPrestamosPorDocumento(@PathVariable String documentoIdentidad) {
        try {
            List<PrestamoDto> prestamos = prestamoNegocio.obtenerPrestamosPorDocumento(documentoIdentidad);
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}



