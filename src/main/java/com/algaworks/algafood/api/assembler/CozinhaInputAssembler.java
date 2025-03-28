package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaIdInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputAssembler {

	public CozinhaIdInput toInput(Cozinha cozinha) {
		
		CozinhaIdInput cozinhaIdInput = new CozinhaIdInput();
		cozinhaIdInput.setId(cozinha.getId());
		
		return cozinhaIdInput;
	}

}
