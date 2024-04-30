package ru.alliedar.pokaznoi.web.mappers;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;

@Mapper(componentModel = "spring")
public interface ClientRequestMapper extends Mappable<Client, ClientRequestDto>{
}
