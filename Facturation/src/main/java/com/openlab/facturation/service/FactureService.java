package com.openlab.facturation.service;

import com.openlab.facturation.dto.CommandeDTO;
import com.openlab.facturation.dto.FactureDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface FactureService {
    Mono<FactureDTO> creerFacture(Long commandeId);
    Mono<CommandeDTO> recupererCommande(Long commandeId);
}
