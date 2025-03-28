package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Data
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipo = TipoStorage.LOCAL;

    public enum TipoStorage {
        LOCAL, S3
    }

    @Data
    public class Local {

        private Path diretorioFotos;
    }

    @Data
    public class S3 {

        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private Regions regiao;
        private String diretorioFotos;

    }
}
