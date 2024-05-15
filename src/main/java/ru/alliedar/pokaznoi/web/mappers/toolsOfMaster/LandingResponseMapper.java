package ru.alliedar.pokaznoi.web.mappers.toolsOfMaster;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Landing;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.LandingResponseDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface LandingResponseMapper extends Mappable<Landing, LandingResponseDto> {
}
