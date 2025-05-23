package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Listar os grupos")
	public List<GrupoModel> listar();
	
	@ApiOperation("Buscar um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)})
	public GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1")
							 Long grupoId);

	@ApiOperation("Adicionar um grupo")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Grupo cadastrado")})
	public GrupoModel adicionar(GrupoInput grupoInput);

	@ApiOperation("Atualizar um grupo")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Grupo atualizado"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)})
	public GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1")
								Long grupoId,
								@ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
	                            GrupoInput grupoInput);

	@ApiOperation("Excluir um grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo exluído"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)})
	public void remover(@ApiParam(value = "ID de um grupo", example = "1")
						Long grupoId);
}
