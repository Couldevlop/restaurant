package com.openlab.utilisateurs.security;


import com.openlab.utilisateurs.entites.User;
import com.openlab.utilisateurs.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Supposons que votre entité User ait une collection de rôles (ou d'autorités)
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // Exemple : ROLE_USER, ROLE_ADMIN, etc.
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Retourne le mot de passe crypté de l'utilisateur
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Retourne le nom d'utilisateur ou l'email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Vous pouvez personnaliser cette logique selon vos besoins
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Vous pouvez personnaliser cette logique si nécessaire
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Si vous gérez l'expiration des identifiants
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // Si vous avez un champ pour vérifier si l'utilisateur est activé
    }
}
