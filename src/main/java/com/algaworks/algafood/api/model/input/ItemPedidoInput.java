package com.algaworks.algafood.api.model.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;

    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    private String observacao;
}
