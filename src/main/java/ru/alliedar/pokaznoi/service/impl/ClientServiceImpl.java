package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.domain.user.Role;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.mappers.client.ClientRegisterMapper;
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
	private final ClientRegisterMapper clientRegisterMapper;
	private final PasswordEncoder passwordEncoder;


	@Override
	@Transactional
	public Client getById(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException("Client not found"));
	}

	@Override
	@Transactional
	public ClientResponseDto create(ClientRegisterDto clientRegisterDto) {
		Optional<Client> clientOptional =
				clientRepository.findByMobileNumber(clientRegisterDto.getMobileNumber());

		if (clientOptional.isPresent()) {
			throw new IllegalArgumentException("Пользователь с номером "
					+ clientRegisterDto.getMobileNumber() + " уже существует.");
		}
		if (!clientRegisterDto.isPasswordConfirmed()) {
			throw new IllegalArgumentException("Пароли не равны ");
		}
		clientRegisterDto.setPassword(passwordEncoder.encode(clientRegisterDto.getPassword()));
		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT+6"));

		Timestamp timestamp = Timestamp.valueOf(localDateTime);


		Client client = clientRegisterMapper.toEntity(clientRegisterDto);
		client.setCreatedAt(timestamp);
		client.setRole(Role.ROLE_USER);
		clientRepository.save(client);
		return clientResponseMapper.toDto(client);
	}
}