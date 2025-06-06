package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

//Fábrica de Specifications
public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltros(PedidoFilter filtro) {
        return (root, query, builder) -> {
            if (Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();

            if (filtro.getClienteId() != null) {
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if (filtro.getRestauranteId() != null) {
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if (filtro.getDataCriacaoInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoInicio()));
            }

            if (filtro.getDataCriacaoFim() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
