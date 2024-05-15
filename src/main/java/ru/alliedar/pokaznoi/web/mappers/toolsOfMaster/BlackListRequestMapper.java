package ru.alliedar.pokaznoi.web.mappers.toolsOfMaster;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListRequestDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface BlackListRequestMapper extends Mappable<BlackList, BlackListRequestDto> {
}
