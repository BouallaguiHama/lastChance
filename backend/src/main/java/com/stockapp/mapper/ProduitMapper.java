package com.stockapp.mapper;

import com.stockapp.dto.ProduitDto;
import com.stockapp.entity.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    
    ProduitDto toDto(Produit produit);
    
    Produit toEntity(ProduitDto produitDto);
    
    void updateEntityFromDto(ProduitDto produitDto, @MappingTarget Produit produit);
}