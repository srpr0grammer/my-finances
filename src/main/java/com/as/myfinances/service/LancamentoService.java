package com.as.myfinances.service;

import com.as.myfinances.model.entity.Lancamento;
import com.as.myfinances.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento salvar (Lancamento lancamento);

    Lancamento atualizar (Lancamento lancamento);

    void deletar (Lancamento lancamento);

    List<Lancamento> buscar (Lancamento lancamento);

    void atualizarStatus (Lancamento lancamentoFiltro, StatusLancamento status);
}
