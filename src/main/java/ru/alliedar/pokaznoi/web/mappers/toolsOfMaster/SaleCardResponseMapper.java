package ru.alliedar.pokaznoi.web.mappers.toolsOfMaster;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardResponseDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface SaleCardResponseMapper extends Mappable<SaleCard, SaleCardResponseDto> {
}
