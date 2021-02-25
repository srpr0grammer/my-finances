package com.as.myfinances.repository;

import com.as.myfinances.model.entity.Lancamento;
import com.as.myfinances.model.enums.StatusLancamento;
import com.as.myfinances.model.enums.TipoLancamento;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void deveSalvarUmLancamento(){
        //cenario
        Lancamento lancamento = criarLancamento();
        //acao
        lancamento = lancamentoRepository.save(lancamento);

        //verificacao
        Assertions.assertThat(lancamento.getId()).isNotNull();
    }

    @Test
    public void deveDeletarUmLancamento(){
        //cenario
        Lancamento lancamento = criarLancamento();
        entityManager.persist(lancamento);

        //acao
        lancamento = entityManager.find(Lancamento.class, lancamento.getId());
        lancamentoRepository.delete(lancamento);
        Lancamento lancamentoDeletado = entityManager.find(Lancamento.class, lancamento.getId());

        //verficacao
        Assertions.assertThat(lancamentoDeletado).isNull();


    }

    @Test
    public void deveAtualizarUmLancamento(){
        //cenario
        Lancamento lancamento = criarEPersistirUmLancamento();
        lancamento = entityManager.find(lancamento.getClass(), lancamento.getId());
        lancamento.setAno(2018);
        lancamento.setDescricao("Teste atualizar");
        lancamento.setStatus(StatusLancamento.CANCELADO);

        //acao
        Lancamento lancamentoAtualizado =  entityManager.find(lancamento.getClass(), lancamento.getId());
        lancamentoRepository.save(lancamento);

        //verificacao
        Assertions.assertThat(lancamentoAtualizado.getAno()).isEqualTo(2018);
        Assertions.assertThat(lancamentoAtualizado.getDescricao()).isEqualTo("Teste atualizar");
        Assertions.assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.CANCELADO);

    }

    @Test
    public void deveBuscarUmLancamentoPorId(){
       //cenario
      Lancamento lancamento = criarEPersistirUmLancamento();

       //acao
      Optional<Lancamento> lancamentoEncontrado =  lancamentoRepository.findById(lancamento.getId());

      //verificacao
        Assertions.assertThat(lancamentoEncontrado.isPresent()).isTrue();
    }

    public Lancamento criarEPersistirUmLancamento(){
        Lancamento lancamento = criarLancamento();
        entityManager.persist(lancamento);
        return lancamento;
    }

    public static Lancamento criarLancamento(){
        return  Lancamento.builder()
                .ano(2019)
                .mes(1)
                .descricao("Lancamento Qualquer")
                .valor(BigDecimal.valueOf(10))
                .tipo(TipoLancamento.RECEITA)
                .status(StatusLancamento.PENDENTE)
                .dataCadastro(LocalDate.now())
                .build();
    }

}
