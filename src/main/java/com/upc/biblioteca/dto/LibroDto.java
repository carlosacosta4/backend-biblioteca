package com.upc.biblioteca.dto;

import com.upc.biblioteca.entity.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDto {
    private String isbn;
    private String titulo;
    private Autor autor;
}
