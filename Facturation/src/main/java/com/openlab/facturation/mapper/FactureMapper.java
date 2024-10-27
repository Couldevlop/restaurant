package com.openlab.facturation.mapper;

import com.openlab.facturation.dto.CommandeDTO;
import com.openlab.facturation.dto.FactureDTO;
import com.openlab.facturation.entity.Facture;
import org.mapstruct.Mapping;

public interface FactureMapper {
    @Mapping(target = "commandeId", source = "commande.id")
    @Mapping(target = "tableNumero", source = "commande.tableNumero")
    @Mapping(target = "montantTotal", source = "commande.montantTotal")
    Facture fromCommandeDTO(CommandeDTO commande);

    FactureDTO toFactureDTO(Facture facture);
}
