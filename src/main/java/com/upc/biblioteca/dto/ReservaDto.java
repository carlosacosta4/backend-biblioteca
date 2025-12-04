package com.upc.biblioteca.dto;

import com.upc.biblioteca.enums.LibroEstado;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDto {
    private Long idReserva;
    private String documentoUsuario;
    private String isbnLibro;
    private LibroEstado estado;
}

