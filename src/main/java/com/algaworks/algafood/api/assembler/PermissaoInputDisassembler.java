package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.PermissaoInput;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao toDomainObjet(PermissaoInput permissaoInput){
        return modelMapper.map(permissaoInput, Permissao.class);
    }

    public void copyToDomainObjet(PermissaoInput permissaoInput, Permissao permissao){
        modelMapper.map(permissaoInput, permissao);
    }
}
