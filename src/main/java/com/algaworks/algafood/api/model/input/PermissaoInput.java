package com.algaworks.algafood.api.model.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissaoInput {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
}
