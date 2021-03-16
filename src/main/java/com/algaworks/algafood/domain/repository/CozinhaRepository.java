package com.algaworks.algafood.domain.repository;


import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{

	//Verifica se existe registro pelo Nome
	boolean existsByNome(String nome);

}
