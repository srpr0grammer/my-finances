package com.as.myfinances.controller;

import com.as.myfinances.exception.RegraNegocioException;
import com.as.myfinances.model.dto.LancamentoDTO;
import com.as.myfinances.model.dto.StatusLancamentoDTO;
import com.as.myfinances.model.entity.Lancamento;
import com.as.myfinances.model.entity.Usuario;
import com.as.myfinances.model.enums.StatusLancamento;
import com.as.myfinances.model.enums.TipoLancamento;
import com.as.myfinances.service.LancamentoService;
import com.as.myfinances.service.UsuarioService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvar( @RequestBody LancamentoDTO lancamentoDTO){

        try {
        Lancamento lancamento = fromDTO(lancamentoDTO);
        lancamento = lancamentoService.salvar(lancamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);

        }
        catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "idUsuario") Long idUsuario
    ){
            Lancamento lancamentoFiltro = new Lancamento();
            lancamentoFiltro.setDescricao(descricao);
            lancamentoFiltro.setMes(mes);
            lancamentoFiltro.setAno(ano);

            Optional<Usuario> usuario = usuarioService.buscarPorId(idUsuario);
            if (!usuario.isPresent()){
                return ResponseEntity.badRequest().body("Não foi possível realizar a consulta ao banco de dados. Usuário não encontrado.");
            } else {
                lancamentoFiltro.setUsuario(usuario.get());
            }

                List<Lancamento> lancamentos = lancamentoService.buscar(lancamentoFiltro);
            return ResponseEntity.ok(lancamentos);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar (@PathVariable ("id") Long id, @RequestBody LancamentoDTO lancamentoDTO){
        return lancamentoService.buscarPorId(id).map(entity -> {
            try {
                Lancamento lancamento = fromDTO(lancamentoDTO);
                lancamento.setId(entity.getId());
                lancamentoService.atualizar(lancamento);

                return ResponseEntity.ok(lancamento);
            }
            catch (RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity("Lancamento nao encontrado na base de dados.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}/atualizar-status")
    public ResponseEntity atualizarStatus(@PathVariable ("id") Long id, @RequestBody StatusLancamentoDTO statusLancamentoDTO) {
        return lancamentoService.buscarPorId(id).map(entity -> {
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(statusLancamentoDTO.getStatus());

            if(statusSelecionado == null ){
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lancamento. Envie um status válido.");
            }
            try {
                entity.setStatus(statusSelecionado);
                lancamentoService.atualizar(entity);
                return ResponseEntity.ok().body(entity);
            }
            catch (RegraNegocioException e) {
                return  ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity("Lnacamento nao encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable ("id") Long id){
        return lancamentoService.buscarPorId(id).map(entity -> {
            try {
                lancamentoService.deletar(entity);
                return ResponseEntity.noContent().build();
            }
            catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity("Lnacamento nao encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }


    //convertendo de Entidade para DTO
    private Lancamento fromDTO(LancamentoDTO dto){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());
        Usuario usuario = usuarioService.buscarPorId(dto.getIdUsuario()).orElseThrow(
                () -> new RegraNegocioException("Usuário não encontrado com este ID"));
        lancamento.setUsuario(usuario);

        if (dto.getTipo() != null) {
        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if (dto.getStatus() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }
        return lancamento;
    }

}
