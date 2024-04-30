package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.mappers.ClientRequestMapper;
import ru.alliedar.pokaznoi.web.mappers.ClientResponseMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
	private final ClientRepository clientRepository;
	private final ClientResponseMapper clientResponseMapper;
	private final ClientRequestMapper clientRequestMapper;

	@Override
	@Transactional
	public ClientResponseDto create(ClientRequestDto clientDto) {
		Optional<Client> clientOptional =
				clientRepository.findByMobileNumber(clientDto.getMobileNumber());

		if (clientOptional.isPresent()) {
			throw new IllegalArgumentException("Пользователь с номером "
					+ clientDto.getMobileNumber() + " уже существует.");
		}
//				if (!userRequestDto.isPasswordConfirmed()) {
//					throw new IllegalArgumentException("Пароли не равны ");
//				}
		Client client = clientRepository.save(
				clientRequestMapper.toEntity(clientDto));
		return clientResponseMapper.toDto(client);
	}
}