package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(nullable = false)
	private BigDecimal subtotal;
	
	@JoinColumn(nullable = false)
	private BigDecimal taxaFrete;
	
	@JoinColumn(nullable = false)
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataConfirmacao;
	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCancelamento;

	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataEntrega;
	
	@JoinColumn(nullable = false)
	@ManyToOne
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	@OneToOne
	private Usuario cliente;
	
	@JoinColumn(nullable = false)
	@Embedded
	private Endereco enderecoEntrega;
	
	@Column(nullable = false)
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();
	
	private StatusPedido status;
	
	
}
