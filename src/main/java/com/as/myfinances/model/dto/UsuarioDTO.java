package com.as.myfinances.model.dto;

import com.as.myfinances.model.entity.Usuario;
import lombok.*;


import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    @Email(message = "Email incorreto!")
    private String email;

    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @NotBlank(message = "Campo obrigatório.")
    private String senha;

    public UsuarioDTO(Usuario obj) {
        id = obj.getId();
        email = obj.getEmail();
        nome = obj.getNome();
        senha = obj.getSenha();

    }
}
