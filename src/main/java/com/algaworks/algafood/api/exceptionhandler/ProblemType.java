package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_INCOMPREENSIVEL ("/mensagem-incompreensivel", "Mensagem Incompreensível"), 
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
	DADOS_INVALIDOS("/dados-invalidos","Dados Inválidos")
	;
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "http//algafood.com.br" + path;
		this.title = title;
	}
	
}
