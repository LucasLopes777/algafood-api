package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    /*
    @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;
    */

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = Path.of("E:\\Estudos\\Algaworks\\Spring Rest\\catalogo",
                novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(),
                Files.newOutputStream(arquivoPath));

        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenas arquivo.",
                e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {

        try {

            Path arquivoPath = getArquivPath(nomeArquivo);

            return FotoRecuperada.builder()
                .inputStream(Files.newInputStream(arquivoPath))
                .build();

        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.",
                e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivo = getArquivPath(nomeArquivo);

            Files.deleteIfExists(arquivo);

        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.",
                e);
        }
    }

    private Path getArquivPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }

}
