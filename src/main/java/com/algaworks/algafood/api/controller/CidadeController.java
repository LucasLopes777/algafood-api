package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path =  "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;   
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<CidadeModel> listar() {
	    List<Cidade> todasCidades = cidadeRepository.findAll();
	    
	    return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
	    Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
	    
	    return cidadeModelAssembler.toModel(cidade);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
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
	
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			 @PathVariable Long cidadeId,
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
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
}
