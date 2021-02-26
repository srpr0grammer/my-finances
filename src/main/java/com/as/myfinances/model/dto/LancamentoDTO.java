package com.as.myfinances.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {

    private Long id;

    @NotEmpty(message = "Campo obrigatório.")
    @Length(min = 3, max = 120, message = "O tamanho deve ser entre 3 e 120 caracteres.")
    private String descricao;

    private Integer ano;
    private Integer mes;
    private BigDecimal valor;
    private Long idUsuario;
    private String tipo;
    private String status;

}
