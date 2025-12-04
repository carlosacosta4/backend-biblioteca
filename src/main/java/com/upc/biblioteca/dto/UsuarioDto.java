package com.upc.biblioteca.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {
    private String documento;
    private String nombre;
    private String apellido;
}
