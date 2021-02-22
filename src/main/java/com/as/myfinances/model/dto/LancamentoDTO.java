package com.as.myfinances.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {

    private Long id;
    private String descricao;
    private Integer ano;
    private Integer mes;
    private BigDecimal valor;
    private Long idUsuario;
    private String tipo;
    private String status;

}
