package com.openlab.utilisateurs.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    // Ajout de ce champ pour gérer l'activation de l'utilisateur
    private boolean enabled;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name= "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


}
