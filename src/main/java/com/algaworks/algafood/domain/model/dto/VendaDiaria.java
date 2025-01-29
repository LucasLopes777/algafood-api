package com.algaworks.algafood.domain.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VendaDiaria {

    private LocalDate data;
    private Long totalVenda;
    private BigDecimal totalFaturado;
}
