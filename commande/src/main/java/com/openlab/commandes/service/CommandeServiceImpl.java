package com.openlab.commandes.service;

import com.openlab.commandes.dto.CommandeDTO;
import com.openlab.commandes.dto.MenuDTO;
import com.openlab.commandes.dto.PlatDTO;
import com.openlab.commandes.entity.Commande;
import com.openlab.commandes.mapper.CommandeMapper;
import com.openlab.commandes.repository.CommandeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImpl implements CommandeService{

    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;

    private final WebClient.Builder webClientBuilder;
    private static final String MENU_SERVICE_URL = "http://menu-service";

    public CommandeServiceImpl(CommandeRepository commandeRepository, CommandeMapper commandeMapper, WebClient.Builder webClientBuilder) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public CommandeDTO createCommande(CommandeDTO commandeDTO) {
        // Calculer le montant total, en utilisant une valeur par défaut si le résultat est null
        double montantTotal = calculerMontantTotal(commandeDTO.getMenuId())
                .defaultIfEmpty(0.0)
                .block();

        commandeDTO.setMontantTotal(montantTotal);
        Commande commande = CommandeMapper.toEntity(commandeDTO);
        return CommandeMapper.toDto(commandeRepository.save(commande));
    }

    @Override
    public CommandeDTO updateCommande(Long id, CommandeDTO commandeDTO) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        commande.setMenuId(commandeDTO.getMenuId());
        commande.setTableNumero(commandeDTO.getTableNumero());

        // Calculer le montant total avec une vérification de null
        double montantTotal = calculerMontantTotal(commandeDTO.getMenuId())
                .defaultIfEmpty(0.0)
                .block();

        commande.setMontantTotal(montantTotal);
        return CommandeMapper.toDto(commandeRepository.save(commande));
    }

    @Override
    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public CommandeDTO getByCommandeId (Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
        return CommandeMapper.toDto(commande);
    }

    @Override
    public List<CommandeDTO> allCommandes() {
        return commandeRepository.findAll().stream()
                .map(CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "menus", key = "#menuId")
    public Mono<Double> calculerMontantTotal(Long menuId) {
        return webClientBuilder.build()
                .get()
                .uri(MENU_SERVICE_URL + "/menus/" + menuId)
                .retrieve()
                .bodyToMono(MenuDTO.class)
                .map(menu -> menu.getPlats().stream()
                        .mapToDouble(PlatDTO::getPrix)
                        .sum()
                )
                .onErrorResume(error -> {
                    System.err.println("Erreur lors de la récupération du montant total du menu : " + error.getMessage());
                    return Mono.just(0.0); // Valeur par défaut en cas d'erreur
                });
    }

}
