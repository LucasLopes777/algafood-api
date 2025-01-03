package com.algaworks.algafood.core.modelMapper;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.domain.model.Endereco;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

		 enderecoToEnderecoModelTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, valeu) -> dest.getCidade().setEstado(valeu));

		return new ModelMapper();
	}


}
