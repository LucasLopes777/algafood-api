package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value = "Cidade", description = "Representa uma cidade")
public class CidadeModel {

	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Pirassununga")
	private String nome;
	
	private EstadoModel estado;
}
