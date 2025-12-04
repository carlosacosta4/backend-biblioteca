package com.upc.biblioteca.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ErrorDto {
    private String mensaje;
    private String detalle;
}
