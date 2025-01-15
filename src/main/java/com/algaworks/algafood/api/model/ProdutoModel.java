package com.algaworks.algafood.api.model;

import lombok.Data;

@Data
public class ProdutoModel {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean ativo;
}
