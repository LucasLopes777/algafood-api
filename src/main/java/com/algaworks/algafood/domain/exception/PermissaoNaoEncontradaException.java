package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends RuntimeException {
    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Não existe um cadastro de permissão com o código %d ",
                permissaoId));
    }
}
