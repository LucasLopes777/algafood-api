package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL) // Só inclui na representação json os valores que não esiverem null
@Getter
@Builder
@ApiModel("Problema")
public class Problem {

	@ApiModelProperty(example = "400", position = 1)
    private Integer status;
	@ApiModelProperty(example = "2025-05-15T20:27:49.4334375Z", position = 5)
    private OffsetDateTime timestamp;
	@ApiModelProperty(example = "http//algafood.com.br/mensagem-incompreensivel", position = 10)
    private String type;
	@ApiModelProperty(example = "Mensagem Incompreensível", position = 15)
    private String title;
	@ApiModelProperty(example = "A propriedade 'complemento' não existe. Corrija ou remova essa propriedade e tente novamente.", position = 20)
    private String detail;
	@ApiModelProperty(example = "A propriedade 'endereco.complemento' não existe. Corrija ou remova essa propriedade e tente novamente.", position = 25)
    private String userMessage;
	
	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position =30)
    private List<Object> objects;

    @Getter
    @Builder
    @ApiModel("ObjetoProblema")
    public static class Object {

    	@ApiModelProperty(example = "complemeento")
        private String name;
    	@ApiModelProperty(example = "complemeento é obrigatório")
        private String userMessage;
    }

}
