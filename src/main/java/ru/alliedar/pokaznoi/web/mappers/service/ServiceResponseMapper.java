package ru.alliedar.pokaznoi.web.mappers.service;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface ServiceResponseMapper extends Mappable<Service, ServiceResponseDto> {
}
