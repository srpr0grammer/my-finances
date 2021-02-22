package com.as.myfinances.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
public class UsuarioDTO {

    @Email(message = "Email incorreto!")
    private String email;

    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @NotBlank(message = "Campo obrigatório.")
    private String senha;
}
