package com.openlab.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatDTO {
    private Long id;
    private String nom;
    private double prix;
    private String description;
}
