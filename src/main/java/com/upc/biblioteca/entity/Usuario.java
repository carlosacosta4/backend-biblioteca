package com.upc.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tbl_usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(length = 200)
    private String nombres;
    @Column(length = 200)
    private String apellidos;
    @Column(length = 200)
    private String correoElectronico;
    @Column(length = 200)
    private String documentoIdentidad;
    @Column(length = 200)
    private String contrasenia;
    @ManyToOne
    @JoinColumn(name = "idRol")
    private Rol rol;

}
