package com.openlab.menu.utils;

import com.openlab.menu.dto.PlatDTO;
import com.openlab.menu.entity.Plat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlatComparator {

    // Vérifie si deux collections de plats sont différentes
    public boolean havePlatsChanged(Set<Plat> existingPlats, Set<PlatDTO> newPlatsDTO) {
        // Comparer la taille des collections
        if (existingPlats.size() != newPlatsDTO.size()) {
            return true;
        }

        // Comparer les IDs des plats
        List<Long> existingPlatIds = getSortedPlatIds(existingPlats);
        List<Long> newPlatIds = getSortedPlatDtoIds(newPlatsDTO);

        if (!existingPlatIds.equals(newPlatIds)) {
            return true;
        }

        // Comparer les attributs de chaque plat
        return newPlatsDTO.stream().anyMatch(platDTO -> {
            Plat existingPlat = findPlatById(existingPlats, platDTO.getId());
            return existingPlat == null || !arePlatAttributesEqual(existingPlat, platDTO);
        });
    }

    // Méthode utilitaire pour obtenir les IDs triés des plats
    private List<Long> getSortedPlatIds(Set<Plat> plats) {
        return plats.stream()
                .map(Plat::getId)
                .sorted()
                .collect(Collectors.toList());
    }

    // Méthode utilitaire pour obtenir les IDs triés des DTO de plats
    private List<Long> getSortedPlatDtoIds(Set<PlatDTO> platsDTO) {
        return platsDTO.stream()
                .map(PlatDTO::getId)
                .sorted()
                .collect(Collectors.toList());
    }

    // Trouver un plat dans la collection existante par ID
    private Plat findPlatById(Set<Plat> plats, Long id) {
        return plats.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Comparer les attributs d'un plat existant et d'un platDTO
    private boolean arePlatAttributesEqual(Plat plat, PlatDTO platDTO) {
        return plat.getNom().equals(platDTO.getNom()) &&
                plat.getPrix() == platDTO.getPrix() &&
                plat.getDescription().equals(platDTO.getDescription());
    }
}
