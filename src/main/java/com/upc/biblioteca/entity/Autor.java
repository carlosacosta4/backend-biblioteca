package com.upc.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tbl_autor")
@Getter
@Setter
@NoArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;
    @Column(length = 300)
    private String nombreAutor;
    @Column(length = 300)
    private String apellidoAutor;
    @Column(length = 600)
    private String biografiaAutor;
}
