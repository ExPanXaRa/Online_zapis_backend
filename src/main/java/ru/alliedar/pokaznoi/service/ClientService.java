package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;

public interface ClientService {
	ClientResponseDto create(ClientRequestDto clientDto);
}
