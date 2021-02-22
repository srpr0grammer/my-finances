package com.as.myfinances.service;

import com.as.myfinances.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario autenticar (String email, String senha);

    Usuario salvarUsuario (Usuario usuario);

    void validarEmail (String email);

    Optional<Usuario> buscarPorId(Long id);
}
