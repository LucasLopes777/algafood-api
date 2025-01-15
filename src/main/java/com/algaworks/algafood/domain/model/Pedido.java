package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    @OneToOne
    private Usuario cliente;

    @JoinColumn(nullable = false)
    @Embedded
    private Endereco enderecoEntrega;

    @Column(nullable = false)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    public void calcularValorTotal() {

        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream() // Obtém uma lista de itens e converte em uma Stream<ItemPedido>
                .map(item -> item.getPrecoTotal()) // Mapeia cada item da lista para o preço total do item (BigDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Soma todos os preços totais, começando do valor BigDecimal.ZERO

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {
        setTaxaFrete(getRestaurante().getTaxaFrete());
    }

    public void atribuirPedidoAosItens() {
        getItens().forEach(item -> item.setPedido(this));
    }
}
