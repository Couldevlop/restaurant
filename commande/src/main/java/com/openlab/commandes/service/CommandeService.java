package com.openlab.commandes.service;

import com.openlab.commandes.dto.CommandeDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface CommandeService {
    CommandeDTO createCommande(CommandeDTO commandeDTO);
    CommandeDTO updateCommande(Long id, CommandeDTO commandeDTO);
    void deleteCommande(Long id);
    CommandeDTO getByCommandeId(Long id);
    List<CommandeDTO> allCommandes();
    Mono<Double> calculerMontantTotal(Long menuId);
}
