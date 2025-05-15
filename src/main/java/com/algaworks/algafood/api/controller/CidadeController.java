package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;   
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
	    List<Cidade> todasCidades = cidadeRepository.findAll();
	    
	    return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value = "Id de uma cidade", example = "1") 
							  @PathVariable Long cidadeId) {
	    Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
	    
	    return cidadeModelAssembler.toModel(cidade);
	}
	
	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") 
								 @RequestBody @Valid CidadeInput cidadeInput) {
	    try {
	        Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
	        
	        cidade = cadastroCidade.salvar(cidade);
	        
	        return cidadeModelAssembler.toModel(cidade);
	    } catch (EstadoNaoEncontradoException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
	/*
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, 
			@RequestBody @Valid Cidade cidade) {
		
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

		// O 3º parâmetro "id" é o atributo q deve ser ignorado
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		try {
			return cadastroCidade.salvar(cidadeAtual);
		} catch (CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	*/
	
	@ApiOperation("Atualiza uma cidade")
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			 @ApiParam(value = "Id de uma nova cidade", example = "1") 
			 @PathVariable Long cidadeId,
			 
			 @ApiParam(name = "corpo", value = "Atualização de uma nova cidade")
	         @RequestBody @Valid CidadeInput cidadeInput) {
	    try {
	        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
	        
	        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
	        
	        cidadeAtual = cadastroCidade.salvar(cidadeAtual);
	        
	        return cidadeModelAssembler.toModel(cidadeAtual);
	    } catch (EstadoNaoEncontradoException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
	@ApiOperation("Exclui uma cidade")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(
			@ApiParam(value = "Id de uma nova cidade", example = "1") 
			@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
}
