package com.upc.biblioteca.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaRequestDto {
    private String documentoIdentidad;
    private String isbnLibro;
}
