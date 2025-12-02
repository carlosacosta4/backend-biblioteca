package com.upc.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tbl_libro")
@Getter
@Setter
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    @Column(length = 300)
    private String tituloLibro;
    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Autor autor;
    @Column(length = 200)
    private String isbnLibro;
    @Column(length = 200)
    private String editorialLibro;
    @Column(length = 200)
    private String anioPublicacionLibro;
    private Long nroPaginasLibro;
    @Column(length = 300)
    private String sinopsisLibro;
    @Column(length = 300)
    private String generoLibro;
    @Column(length = 500)
    private String rutaImagenLibro;
    private Long cantidadLibro;
}
