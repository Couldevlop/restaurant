package com.openlab.facturation.service;

import com.openlab.facturation.dto.CommandeDTO;
import com.openlab.facturation.dto.FactureDTO;
import com.openlab.facturation.mapper.FactureMapper;
import com.openlab.facturation.repository.FactureRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FactureServiceImpl implements FactureService{
    private final FactureRepository factureRepository;
    private final WebClient.Builder webClientBuilder;
    private final FactureMapper factureMapper;

    public FactureServiceImpl(FactureRepository factureRepository, WebClient.Builder webClientBuilder, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.webClientBuilder = webClientBuilder;
        this.factureMapper = factureMapper;
    }

    private static final String COMMANDE_SERVICE_URL ="http://commande-service";
    private static final String TABLE_SERVICE_URL = "http://table-service";

    @Override
    public Mono<FactureDTO> creerFacture(Long commandeId) {
        return recupererCommande(commandeId)
                .map(factureMapper::fromCommandeDTO)
                .map(factureRepository::save)
                .map(factureMapper::toFactureDTO)
                .onErrorResume(error ->{
                    System.err.println("Erreur lors de la création de la facture : " + error.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    @Cacheable(value = "commandes", key = "#commandeId")
    public Mono<CommandeDTO> recupererCommande(Long commandeId) {
        return webClientBuilder.build()
                .get()
                .uri(COMMANDE_SERVICE_URL + "/commandes/" + commandeId)
                .retrieve()
                .bodyToMono(CommandeDTO.class)
                .doOnError(error -> System.err.println("Erreur lors de la récupération de la commande : " + error.getMessage()));
    }
}
