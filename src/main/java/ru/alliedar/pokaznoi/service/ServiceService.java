package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceRequestDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;

public interface ServiceService {
	ServiceResponseDto create(ServiceRequestDto serviceRequestDto);
}
