package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + " - " + nomeOriginal;
    }

    default void sustituir(String nomeArquivoAntigo,
                           NovaFoto novaFoto) {

        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }

    void remover(String nomeArquivo);

    @Builder
    @Getter
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;

    }

}
