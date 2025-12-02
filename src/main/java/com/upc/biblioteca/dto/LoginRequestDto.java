package com.upc.biblioteca.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

    private String correoElectronico;
    private String password;

}
