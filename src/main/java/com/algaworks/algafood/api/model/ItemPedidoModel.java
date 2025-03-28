package com.algaworks.algafood.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoModel {
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}
