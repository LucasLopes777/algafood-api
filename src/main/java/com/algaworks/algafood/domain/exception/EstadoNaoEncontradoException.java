package com.algaworks.algafood.domain.exception;

public  class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException(Long estadoId) {
		super(String.format("Não existe um cadastro de Estado com o código %d", estadoId));
	}
	
}
