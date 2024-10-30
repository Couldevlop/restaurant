package com.openlab.commandes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private Long id;
    private String nom;
    private Set<PlatDTO> plats;
}
