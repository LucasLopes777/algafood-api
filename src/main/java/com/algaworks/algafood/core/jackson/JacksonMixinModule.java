package com.algaworks.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.mixin.CidadeMixin;
import com.algaworks.algafood.api.model.mixin.CozinhaMixin;
import com.algaworks.algafood.api.model.mixin.RestauranteMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {

		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
	}
}
