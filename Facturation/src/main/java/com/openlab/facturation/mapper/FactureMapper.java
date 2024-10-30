package com.openlab.facturation.mapper;

import com.openlab.facturation.dto.CommandeDTO;
import com.openlab.facturation.dto.FactureDTO;
import com.openlab.facturation.entity.Facture;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class FactureMapper {
    // Méthode pour transformer un CommandeDTO en Facture
    public Facture fromCommandeDTO(CommandeDTO commande) {
        Facture facture = new Facture();
        facture.setCommandeId(commande.getId()); // Assure que CommandeDTO a bien un champ 'id'
        facture.setTableNumero(commande.getTableNumero());
        facture.setMontantTotal(commande.getMontantTotal());
        facture.setDateFacture(LocalDateTime.now()); // Date de la facture définie à la date actuelle
        return facture;
    }

    // Méthode pour transformer une Facture en FactureDTO
    public FactureDTO toFactureDTO(Facture facture) {
        FactureDTO factureDTO = new FactureDTO();
        factureDTO.setId(facture.getId());
        factureDTO.setCommandeId(facture.getCommandeId());
        factureDTO.setTableNumero(facture.getTableNumero());
        factureDTO.setMontantTotal(facture.getMontantTotal());
        factureDTO.setDateFacture(facture.getDateFacture());
        return factureDTO;
    }
}
