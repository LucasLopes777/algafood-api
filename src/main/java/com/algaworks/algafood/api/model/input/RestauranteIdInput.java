package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RestauranteIdInput {

	@ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
}
