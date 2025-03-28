package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    FotoRecuperada recuperar(String nomeArquivo);

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
        private String contentType;
        private InputStream inputStream;

    }

    @Builder
    @Getter
    class FotoRecuperada {

        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean teminputStream() {
            return inputStream != null;
        }
    }


}
