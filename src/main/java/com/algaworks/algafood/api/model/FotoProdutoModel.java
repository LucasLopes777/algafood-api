package com.algaworks.algafood.api.model;

import lombok.Data;

@Data
public class FotoProdutoModel {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

}
