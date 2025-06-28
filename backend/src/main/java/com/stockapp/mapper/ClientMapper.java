package com.stockapp.mapper;

import com.stockapp.dto.ClientDto;
import com.stockapp.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    
    ClientDto toDto(Client client);
    
    Client toEntity(ClientDto clientDto);
    
    void updateEntityFromDto(ClientDto clientDto, @MappingTarget Client client);
}