package com.upc.biblioteca.dto;

import com.upc.biblioteca.entity.Autor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDto {
    private String isbn;
    private String titulo;
    private Autor autor;
}
