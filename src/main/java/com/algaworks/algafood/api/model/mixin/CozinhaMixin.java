package com.algaworks.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CozinhaMixin {

//	@JsonIgnore Serve para ignorar o atributo tanto no json quanto xml
//	@JsonProperty("titulo") Serve para personalizar o nome do atributo tanto no json quanto xml
	private String nome;

	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();
}
