package com.upc.biblioteca.entity;

import com.upc.biblioteca.enums.LibroEstado;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_libro")
    private Libro libro;

    @Enumerated(EnumType.STRING)
    private LibroEstado estado;
}
