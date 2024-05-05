package ru.alliedar.pokaznoi.web.mappers.master;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseChangeDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface MasterResponseChangeMapper extends Mappable<Master, MasterResponseChangeDto> {
}
