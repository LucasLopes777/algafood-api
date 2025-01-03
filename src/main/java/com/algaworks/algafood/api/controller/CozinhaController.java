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

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;   
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	/*
	// produces = MediaType.APPLICATION_JSON_VALUE uma forma de fazer o método só retornar json
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}
	*/
	@GetMapping
	public List<CozinhaModel> listar() {
	    List<Cozinha> todasCozinhas = cozinhaRepository.findAll();
	    
	    return cozinhaModelAssembler.toCollectionModel(todasCozinhas);
	}
	
	//Apenas como referência para retornar no formato XML
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}
	
//	@ResponseStatus(HttpStatus.CREATED)
//	@GetMapping("/{cozinhaId}")
//	public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
//		return cozinhaRepository.buscar(id);
//	}
	
	/*
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
		return cadastroCozinha.buscarOuFalhar(id);
		
		Exemplo de um redirect
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
		
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.headers(headers)
				.build();
		 
		
	}
	*/
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
	    Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
	    cozinha = cadastroCozinha.salvar(cozinha);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,
	        @RequestBody @Valid CozinhaInput cozinhaInput) {
		
	    Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
	    cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toModel(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
	
}
