package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteModel> listar() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @JsonView(RestauranteView.ApenasNomes.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteModel> listarApenasNome() {
        return listar();
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

        try {
        	Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
        	
            return cadastroRestauranteService.salvar(restaurante);
        } catch (RestauranteNaoEncontradoException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
	/*
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar (@PathVariable Long restauranteId, 
			@RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			
			// a partir do 3º parâmetro são os atributo q devem ser ignorados
			BeanUtils.copyProperties(restaurante, restauranteAtual, 
					"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	*/

    //11.17. Mapeando para uma instância destino (e não um tipo) com ModelMapper
    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

            restauranteInputDisassembler.copyToDomainobject(restauranteInput, restauranteAtual);

            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (RestauranteNaoEncontradoException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @PatchMapping("/{restauranteId}")
    public RestauranteModel atualizarParcial(@PathVariable Long restauranteId,
                                             @RequestBody Map<String, Object> campos, HttpServletRequest request) {

        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        merge(campos, restauranteAtual, request);

        validate(restauranteAtual, "restaurante");

        return atualizar(restauranteId, restauranteModelAssembler.toInput(restauranteAtual));

    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.desativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestauranteService.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestauranteService.desativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        cadastroRestauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
	    cadastroRestauranteService.fechar(restauranteId);
	}

	private void validate(Restaurante restaurante, String objectName) {
	
	    //9.19. Executando processo de validação programaticamente
	    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
	
	    validator.validate(restaurante, bindingResult);
	
	    if (bindingResult.hasErrors()) {
	        throw new ValidacaoException(bindingResult);
	    }
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
	                   HttpServletRequest request) {
	
	    ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
	
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
	
	        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
	
	        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
	            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
	
	            //Torna os atributos Private acessíveis
	            field.setAccessible(true);
	
	            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
	
	            ReflectionUtils.setField(field, restauranteDestino, novoValor);
	
	        });
	
	    } catch (IllegalArgumentException e) {
	        Throwable rootCause = ExceptionUtils.getRootCause(e);
	        throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
	    }
	}
}
