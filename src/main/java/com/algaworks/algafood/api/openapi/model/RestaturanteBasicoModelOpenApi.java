package com.algaworks.algafood.api.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteBasicoModel")
@Setter
@Getter
public class RestaturanteBasicoModelOpenApi {

	@ApiModelProperty(example = "1")
    private Long id;
	@ApiModelProperty(example = "Thay Gourmet")
    private String nome;
	@ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;
	
    private CozinhaModel cozinha;
}
