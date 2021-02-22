package com.as.myfinances.service.impl;

import com.as.myfinances.model.entity.Lancamento;
import com.as.myfinances.model.enums.StatusLancamento;
import com.as.myfinances.repository.LancamentoRepository;
import com.as.myfinances.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl (LancamentoRepository lancamentoRepository){
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        //garantindo que esse metodo so irá receber um id para realizar a atualização
        Objects.requireNonNull(lancamento.getId());
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        lancamentoRepository.delete(lancamento);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        //withIgnoreCase(): Ignora se o usuario pesquisou com caixa baixa ou alta
        //withStringMatcher: indica a forma que o meotod vai buscar as string na base de dados.,
        Example example = Example.of(lancamentoFiltro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return lancamentoRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }
}
