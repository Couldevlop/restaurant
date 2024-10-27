package com.openlab.facturation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandeDTO {
    private Long id;
    private int tableNumero;
    private double montantTotal;
}
