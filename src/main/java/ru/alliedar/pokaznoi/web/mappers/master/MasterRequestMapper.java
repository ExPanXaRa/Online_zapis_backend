package ru.alliedar.pokaznoi.web.mappers.master;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.web.dto.master.MasterRequestDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface MasterRequestMapper extends Mappable<Master, MasterRequestDto> {
}
