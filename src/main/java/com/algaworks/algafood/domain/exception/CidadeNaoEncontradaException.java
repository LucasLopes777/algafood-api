package com.algaworks.algafood.domain.exception;

public  class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException(Long cidadeId) {
		super(String.format("Não existe um cadastro de Cidade com o código %d", cidadeId));
	}
	
}
