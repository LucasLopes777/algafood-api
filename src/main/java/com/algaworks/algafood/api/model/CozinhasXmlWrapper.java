package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "cozinhas")
public class CozinhasXmlWrapper {

	@JsonProperty("cozinha")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Cozinha> cozinhas;

	public CozinhasXmlWrapper(List<Cozinha> cozinhas) {
		this.cozinhas = cozinhas;
	}

	public List<Cozinha> getCozinhas() {
		return cozinhas;
	}

	public void setCozinhas(List<Cozinha> cozinhas) {
		this.cozinhas = cozinhas;
	}
	
	
}
