package com.algaworks.algafood.api.model.input;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RestauranteIdInput {

    @NotNull
    private Long id;
}
