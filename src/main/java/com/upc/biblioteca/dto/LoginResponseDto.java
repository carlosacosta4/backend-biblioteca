package com.upc.biblioteca.dto;

import com.upc.biblioteca.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private Header encabezado;
    private String token;
    private String nombres;
    private String apellidos;
    private Rol rol;


}
