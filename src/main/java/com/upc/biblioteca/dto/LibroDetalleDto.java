package com.upc.biblioteca.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDetalleDto {
    private String isbn;
    private String titulo;
    private String autor;
    private String rutaImagen;
    private String editorial;
    private String anioPublicacion;
    private Long nroPaginas;
    private String sinopsis;
    private String genero;
    private Long cantidad;
}

