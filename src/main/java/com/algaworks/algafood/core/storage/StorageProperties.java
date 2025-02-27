package com.algaworks.algafood.core.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Data
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

    private Local local = new Local();

    @Data
    public class Local {

        private Path diretorioFotos;
    }

    @Data
    public class S3 {

        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private String regiao;
        private String diretorioFotos;

    }
}
