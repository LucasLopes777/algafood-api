package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	Page<CozinhaModel> listar(Pageable pageable);
	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel buscar(@ApiParam(value = "Id de uma cozinha", example = "1", required = true)
							   Long cozinhaId);
	
	@ApiOperation("Cadastra uma cozinha")
	@ApiResponse(code = 201, message = "Cozinha cadastrada com sucesso")
	CozinhaModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
								  CozinhaInput cozinhaInput);

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha atualizada com sucesso"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel atualizar(@ApiParam(value = "Id de uma cozinha", example = "1", required = true)
								  Long cozinhaId,
			 					  @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true)
								  CozinhaInput cozinhaInput);
	
	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída com sucesso"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "Id de uma cozinha", example = "1", required = true)
						Long cozinhaId);
	
}
