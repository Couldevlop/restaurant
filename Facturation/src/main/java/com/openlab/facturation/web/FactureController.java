package com.openlab.facturation.web;

import com.openlab.facturation.dto.CommandeDTO;
import com.openlab.facturation.dto.FactureDTO;
import com.openlab.facturation.service.FactureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/factures")
@RestController
public class FactureController {
    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping("/{commandeId}")
    public Mono<ResponseEntity<FactureDTO>> creerFacture(@PathVariable Long commandeId) {
        return factureService.creerFacture(commandeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/factures/commande/{commandeId}")
    public Mono<CommandeDTO> recupererCommande(@PathVariable Long commandeId) {
        return factureService.recupererCommande(commandeId);
    }
}
