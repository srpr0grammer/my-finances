package com.as.myfinances.controller;

import com.as.myfinances.controller.exception.ErrorAutenticationException;
import com.as.myfinances.controller.exception.RegraNegocioException;
import com.as.myfinances.model.dto.UsuarioDTO;
import com.as.myfinances.model.entity.Usuario;
import com.as.myfinances.service.LancamentoService;
import com.as.myfinances.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody UsuarioDTO usuarioDTO){
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
    public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO) {

        try {
        Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());

        return ResponseEntity.ok(usuarioAutenticado);

        }catch (ErrorAutenticationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldoPorUsuario (@PathVariable("id") Long id){
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);

        //testando se o id do usuario que está sendo consultaod existe na base.
        if (!usuario.isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);

        return ResponseEntity.ok(saldo);
    }

    @GetMapping
    private ResponseEntity<List<UsuarioDTO>> buscarUsuario (UsuarioDTO usuarioDTO){
        List<Usuario> listUsuario = usuarioService.buscarUsuario();
        List<UsuarioDTO> listDto = listUsuario.stream()
                .map(obj -> new UsuarioDTO(obj))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }

    //Conversao Entidade para DTO
    private Usuario fromDTO (UsuarioDTO dto){
        Usuario usuario = Usuario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();

        return usuario;
    }

}
