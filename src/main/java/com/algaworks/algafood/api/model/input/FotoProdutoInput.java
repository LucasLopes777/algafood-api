package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
