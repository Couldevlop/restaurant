package com.openlab.facturation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FactureDTO {
    private Long id;
    private Long commandeId;
    private Long tableNumero;
    private double montantTotal;
    private LocalDateTime dateFacture;
}
