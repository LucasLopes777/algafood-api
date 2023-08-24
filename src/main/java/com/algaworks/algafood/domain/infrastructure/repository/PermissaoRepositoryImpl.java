package com.algaworks.algafood.domain.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Permissao> listar() {
		TypedQuery<Permissao> query = manager.createQuery("from Permissao", Permissao.class);
		return query.getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}

	@Override
	public Permissao salvar(Permissao permissao) {
		return manager.merge(permissao);
	}

	@Override
	public void remover(Permissao permissao) {
		permissao = manager.find(Permissao.class, permissao.getId());
		manager.remove(permissao);
	}

}
