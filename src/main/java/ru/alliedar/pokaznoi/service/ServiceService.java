package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceRequestDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;

import java.util.List;

public interface ServiceService {

    boolean isServiceOwner(Long userId, Long serviceId);
    ServiceResponseDto create(ServiceRequestDto serviceRequestDto);

    List<ServiceResponseDto> getAll();

    ServiceResponseDto getById(Long id);

    ServiceResponseDto update(ServiceRequestDto serviceRequestDto, Long id);

    void delete(Long id);
}
