package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository 
	extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, 
	JpaSpecificationExecutor<Restaurante> {
	
	//Resolvendo o Problema do N+1 com fetch join na JPQL (aula 6.14)
	@Query(" from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();

	// Exemplo de JPQP
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	public List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
	
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinha);
}
