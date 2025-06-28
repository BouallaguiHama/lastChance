package com.stockapp.mapper;

import com.stockapp.dto.FournisseurDto;
import com.stockapp.entity.Fournisseur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {
    
    FournisseurDto toDto(Fournisseur fournisseur);
    
    Fournisseur toEntity(FournisseurDto fournisseurDto);
    
    void updateEntityFromDto(FournisseurDto fournisseurDto, @MappingTarget Fournisseur fournisseur);
}