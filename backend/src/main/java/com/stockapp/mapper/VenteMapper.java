package com.stockapp.mapper;

import com.stockapp.dto.VenteDto;
import com.stockapp.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VenteMapper {
    
    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "produit.nom", target = "nomProduit")
    @Mapping(source = "client.nom", target = "nomClient")
    @Mapping(source = "client.prenom", target = "prenomClient")
    VenteDto toDto(Vente vente);
    
    @Mapping(target = "produit", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "facture", ignore = true)
    @Mapping(target = "bonDeLivraison", ignore = true)
    Vente toEntity(VenteDto venteDto);
}