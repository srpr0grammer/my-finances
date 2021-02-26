package com.as.myfinances.service.impl;

import com.as.myfinances.controller.exception.ErrorAutenticationException;
import com.as.myfinances.controller.exception.RegraNegocioException;
import com.as.myfinances.model.entity.Usuario;
import com.as.myfinances.repository.UsuarioRepository;
import com.as.myfinances.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        //Testando se o email esta presente
        if (!usuario.isPresent()){
            throw new ErrorAutenticationException("Email não encontrado!");
        }
        //testens se a senha esta presente.
        if (!usuario.get().getSenha().equals(senha)) {
            throw new ErrorAutenticationException("Senha inválida!");
        }
        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existeEmail = usuarioRepository.existsByEmail(email);
        if (existeEmail){
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }

    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {

        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> buscarUsuario() {
        return usuarioRepository.findAll();
    }


}
