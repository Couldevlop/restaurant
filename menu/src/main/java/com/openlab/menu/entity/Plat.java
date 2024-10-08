package com.openlab.menu.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Plat {
    private Long id;
    private String nom;
    private double prix;
    private String description;
}
