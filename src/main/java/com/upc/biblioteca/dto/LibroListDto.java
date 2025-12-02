package com.upc.biblioteca.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroListDto {
    private String imagen;
    private String titulo;
    private String autor;
    private String isbn;

}

