package com.openlab.commandes.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandeDTO {
    private Long id;
    private Long menuId;
    private int tableNumero;
    private double montantTotal;
    private LocalDate dateCommande;
}
