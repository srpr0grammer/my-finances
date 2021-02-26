package com.as.myfinances.model.dto;

import com.as.myfinances.model.entity.Usuario;
import lombok.*;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;


import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    @Email(message = "Email incorreto!")
    @NotEmpty(message = "Email obrigatório.")
    private String email;

    @NotEmpty(message = "Nome obrigatória.")
    @Length(min = 3, max = 120, message = "O tamanho deve ser entre 3 e 120 caracteres.")
    private String nome;

    @NotEmpty(message = "Senha obrigatória.")
    @Length(min = 6, max = 20, message = "O tamanho deve ser entre 6 e 20 caracteres.")
    private String senha;

    public UsuarioDTO(Usuario obj) {
        id = obj.getId();
        email = obj.getEmail();
        nome = obj.getNome();
        senha = obj.getSenha();

    }
}
