package com.as.myfinances.controller;

import com.as.myfinances.exception.ErrorAutenticationException;
import com.as.myfinances.exception.RegraNegocioException;
import com.as.myfinances.model.dto.UsuarioDTO;
import com.as.myfinances.model.entity.Usuario;
import com.as.myfinances.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity save(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = fromDTO(usuarioDTO);

        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
        return  new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }

        catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO){

        try{
        Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());

        return ResponseEntity.ok(usuarioAutenticado);

        }catch (ErrorAutenticationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Conversao Entidade para DTO
    public Usuario fromDTO (UsuarioDTO dto){
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();

        return usuario;
    }

}
