package com.algaworks.algafood.api.model.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ProdutoInput {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    @PositiveOrZero
    private Double preco;
    @NotNull
    private Boolean ativo;
}
