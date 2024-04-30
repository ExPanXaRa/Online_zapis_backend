package ru.alliedar.pokaznoi.web.mappers;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;

@Mapper(componentModel = "spring")
public interface ClientResponseMapper extends Mappable<Client, ClientResponseDto>{

}
