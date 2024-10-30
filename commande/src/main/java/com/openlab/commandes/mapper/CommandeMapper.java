package com.openlab.commandes.mapper;

import com.openlab.commandes.dto.CommandeDTO;
import com.openlab.commandes.entity.Commande;
import org.springframework.stereotype.Component;

@Component
public class CommandeMapper {

    public static CommandeDTO toDto(Commande commande) {
        return CommandeDTO.builder()
                .id(commande.getId())
                .menuId(commande.getMenuId())
                .tableNumero(commande.getTableNumero())
                .montantTotal(commande.getMontantTotal())
                .dateCommande(commande.getDateCommande())
                .build();
    }

    public static Commande toEntity(CommandeDTO dto) {
        return Commande.builder()
                .id(dto.getId())
                .menuId(dto.getMenuId())
                .tableNumero(dto.getTableNumero())
                .montantTotal(dto.getMontantTotal())
                .dateCommande(dto.getDateCommande())
                .build();
    }
}
