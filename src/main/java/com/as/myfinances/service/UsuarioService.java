package com.as.myfinances.service;

import com.as.myfinances.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar (String email, String senha);

    Usuario salvarUsuario (Usuario usuario);

    void validarEmail (String email);
}
