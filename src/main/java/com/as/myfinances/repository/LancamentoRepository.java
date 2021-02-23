package com.as.myfinances.repository;

import com.as.myfinances.model.entity.Lancamento;
import com.as.myfinances.model.enums.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "SELECT sum(l.valor) FROM Lancamento l JOIN l.usuario u " +
                   "WHERE u.id = :idUsuario " +
                   "AND l.tipo =:tipo GROUP BY u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long id,
                                                   @Param("tipo") TipoLancamento tipo);
}
