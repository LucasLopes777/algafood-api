package com.algaworks.algafood.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class VendaDiaria {

    private Date data;
    private Long totalVenda;
    private BigDecimal totalFaturado;
}
