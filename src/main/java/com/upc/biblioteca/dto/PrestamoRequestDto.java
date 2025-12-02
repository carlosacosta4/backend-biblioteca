package com.upc.biblioteca.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoRequestDto {
    private String documentoIdentidad;
    private String isbnLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
}

