package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	List<CidadeModel> listar();
	
	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
	CidadeModel buscar(@ApiParam(value = "Id de uma cidade", example = "1", required = true) 
							  Long cidadeId);
	
	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade Cadastrada")})
	CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) 
								 CidadeInput cidadeInput);
	
	@ApiOperation("Atualiza uma cidade")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
	CidadeModel atualizar(@ApiParam(value = "Id de uma cidade", example = "1", required = true) 
								 Long cidadeId,
								 @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true)
						         CidadeInput cidadeInput);
	
	@ApiOperation("Exclui uma cidade")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
	void excluir(@ApiParam(value = "Id de uma cidade", example = "1", required = true)
						@PathVariable Long cidadeId);
	
}
