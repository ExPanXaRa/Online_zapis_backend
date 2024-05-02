package ru.alliedar.pokaznoi.web.mappers.toolsOfMaster;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Landing;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.LandingRequestDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface LandingRequestMapper extends Mappable<Landing, LandingRequestDto> {
}
