package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Cidade;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoModel {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemeento;
    private String bairro;
    private CidadeResumoModel cidade;

}
