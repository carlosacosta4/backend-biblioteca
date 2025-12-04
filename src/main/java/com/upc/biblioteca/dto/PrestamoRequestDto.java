package com.upc.biblioteca.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate fechaPrestamo;

    @JsonFormat(pattern = "yyyy-M-d")
    private LocalDate fechaDevolucion;
}

