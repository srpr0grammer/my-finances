package com.as.myfinances.service;


import com.as.myfinances.model.entity.Lancamento;
import com.as.myfinances.model.enums.StatusLancamento;
import com.as.myfinances.repository.LancamentoRepository;
import com.as.myfinances.repository.LancamentoRepositoryTest;
import com.as.myfinances.service.impl.LancamentoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    private LancamentoServiceImpl service;

    @MockBean
    private LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento(){
        // cenario
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1L);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        //acao execucao
        Lancamento lancamento = service.salvar(lancamentoASalvar);

        //verificacao
        Assertions.assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.PENDENTE);
    }

    @Test
    public void deveAtualizarUmLancamento(){
        // cenario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1L);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

        Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

        //acao execucao
        Lancamento lancamento = service.atualizar(lancamentoSalvo);

        //verificacao
        Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);
    }

    @Test
    public void deveLancarErroAoTentarAtualizarUmLancamentoQueAindaNaoFoiSalvo(){
        //ceario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();

        //acano
        Assertions.catchThrowableOfType(() -> service.atualizar(lancamentoSalvo), NullPointerException.class);
        Mockito.verify(repository, Mockito.never()).save(lancamentoSalvo);
    }

    @Test
    public void deveDeletarUmLancamento(){
        //cenario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1L);

        //execucao
        service.deletar(lancamentoSalvo);

        //verificacao
        Mockito.verify(repository).delete(lancamentoSalvo);
    }

    @Test
    public void deveLancarErroAotentarDeletarUmLancamentoQueAindaNaoFoiSalvo(){
        //cenario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();

        //execucao
        Assertions.catchThrowableOfType(() -> service.deletar(lancamentoSalvo), NullPointerException.class);

        //verificacao
        Mockito.verify( repository, Mockito.never()).delete(lancamentoSalvo);
    }

    @Test
    public void deveFiltrarLancamento(){
        //cenario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1L);
        List<Lancamento> list = Arrays.asList(lancamentoSalvo);
        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(list);

        //execucao
       List<Lancamento> resultado =  service.buscar(lancamentoSalvo);

       //verificacao
        Assertions.assertThat(resultado).isNotEmpty()
                .hasSize(1)
                .contains(lancamentoSalvo);
    }

    @Test
    public void deveAtualizarStatusDeUmLancamento(){
        //cenario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1L);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

        StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
        Mockito.doReturn(lancamentoSalvo).when(service).atualizar(lancamentoSalvo);

        //execucao
        service.atualizarStatus(lancamentoSalvo, novoStatus);

        ///verificacao
        Assertions.assertThat(lancamentoSalvo.getStatus()).isEqualTo(novoStatus);
        Mockito.verify(service).atualizar(lancamentoSalvo);
    }

    @Test
    public void deveBuscarUmLancamentoPorId(){
        //cenario
        Long id = 1L;
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamentoSalvo));

        //execucao
        Optional<Lancamento> resultado = service.buscarPorId(id);

        //verificacao
        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    public void deveRetonarVazioQuandoOLancamentoNaoExiste() {
        //cenario
        Long id = 1L;
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execucao
        Optional<Lancamento> resultado = service.buscarPorId(id);

        //verificacao
        Assertions.assertThat(resultado.isPresent()).isFalse();
    }
}
