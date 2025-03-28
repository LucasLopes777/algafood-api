package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @Embedded
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;
    ;

    public void calcularValorTotal() {

        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream() // Obtém uma lista de itens e converte em uma Stream<ItemPedido>
                .map(item -> item.getPrecoTotal()) // Mapeia cada item da lista para o preço total do item (BigDecimal)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add); // Soma todos os preços totais, começando do valor BigDecimal.ZERO

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {
        setTaxaFrete(getRestaurante().getTaxaFrete());
    }

    public void atribuirPedidoAosItens() {
        getItens().forEach(item -> item.setPedido(this));
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());

        registerEvent(new PedidoCanceladoEvent(this));
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser altarado de %s para %s",
                            getCodigo(),
                            getStatus().getDescricao(),
                            novoStatus.getDescricao())
            );
        }

        this.status = novoStatus;
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }
}
