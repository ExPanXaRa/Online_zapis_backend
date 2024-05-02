package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.mappers.client.ClientRequestMapper;
import ru.alliedar.pokaznoi.web.mappers.client.ClientResponseMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
	private final ClientRepository clientRepository;
	private final ClientResponseMapper clientResponseMapper;
	private final ClientRequestMapper clientRequestMapper;


	@Override
	@Transactional
	public Client getById(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException("Client not found"));
	}

	@Override
	@Transactional
	public ClientResponseDto create(ClientRequestDto clientDto) {
		Optional<Client> clientOptional =
				clientRepository.findByMobileNumber(clientDto.getMobileNumber());

		if (clientOptional.isPresent()) {
			throw new IllegalArgumentException("Пользователь с номером "
					+ clientDto.getMobileNumber() + " уже существует.");
		}
		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT+6"));

// Создание объекта Timestamp из LocalDateTime
		Timestamp timestamp = Timestamp.valueOf(localDateTime);

// Установка созданного Timestamp в ваш объект clientRequestDto
//		clientDto.setCreatedAt(timestamp);
//				if (!userRequestDto.isPasswordConfirmed()) {
//					throw new IllegalArgumentException("Пароли не равны ");
//				}

		Client client = clientRequestMapper.toEntity(clientDto);
		client.setCreatedAt(timestamp);
		clientRepository.save(client);
		return clientResponseMapper.toDto(client);
	}
}