package com.upc.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tbl_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    @Column(length = 200)
    private String descripcion;
}
