package com.openlab.commandes.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatDTO {
    private Long id;
    private String nom;
    private double prix;
    private String description;
}
