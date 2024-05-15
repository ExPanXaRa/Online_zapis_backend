package ru.alliedar.pokaznoi.web.mappers.client;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface ClientRegisterMapper extends Mappable<Client, ClientRegisterDto> {
}
