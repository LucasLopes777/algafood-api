package com.algaworks.algafood.domain.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<FormaPagamento> listar() {
		TypedQuery<FormaPagamento> query = manager.createQuery("from FormaPagamento", FormaPagamento.class);
	
		return query.getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return manager.find(FormaPagamento.class, id);
	}

	@Override
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
	}

	@Override
	@Transactional
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = manager.find(FormaPagamento.class, formaPagamento.getId());
		manager.remove(formaPagamento);
	}

}
