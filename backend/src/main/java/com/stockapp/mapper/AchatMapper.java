package com.stockapp.mapper;

import com.stockapp.dto.AchatDto;
import com.stockapp.entity.Achat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AchatMapper {
    
    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "fournisseur.id", target = "fournisseurId")
    @Mapping(source = "produit.nom", target = "nomProduit")
    @Mapping(source = "fournisseur.nom", target = "nomFournisseur")
    AchatDto toDto(Achat achat);
    
    @Mapping(target = "produit", ignore = true)
    @Mapping(target = "fournisseur", ignore = true)
    @Mapping(target = "bonDAchat", ignore = true)
    Achat toEntity(AchatDto achatDto);
}