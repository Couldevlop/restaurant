package com.openlab.menu.dto;

import lombok.*;

import java.util.Set;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long id;
    private String nom;
    private Set<PlatDTO> plats;
}
