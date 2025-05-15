package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {

	@NotBlank
	@ApiModelProperty(example = "1", required = true)
	private String nome;
	
	@Valid
	@NotNull
	@ApiModelProperty(example = "São Paulo")
	private EstadoIdInput estado;
}
