package com.as.myfinances.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {

    private Long id;

    @NotEmpty(message = "Campo obrigatório.")
    private String descricao;
    private Integer ano;
    private Integer mes;
    private BigDecimal valor;
    private Long idUsuario;
    private String tipo;
    private String status;

}
