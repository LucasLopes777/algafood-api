       package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
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
	private CadastroCozinhaService cadastroCozinhaService;
	
	// produces = MediaType.APPLICATION_JSON_VALUE uma forma de fazer o método só retornar json
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
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
	
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
		return cadastroCozinhaService.buscarOuFalhar(id);
		
		/* Exemplo de um redirect
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
		
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.headers(headers)
				.build();
		 */
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		try {
			return cadastroCozinhaService.salvar(cozinha);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId, 
			@RequestBody @Valid Cozinha cozinha) {
		
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		
		// O 3º parâmetro "id" é o atributo q deve ser ignorado
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); 
		
		try {
			return cadastroCozinhaService.salvar(cozinhaAtual);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cozinhaId) {
		cadastroCozinhaService.excluir(cozinhaId);
	}
	
}
