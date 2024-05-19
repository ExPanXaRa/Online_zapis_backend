package ru.alliedar.pokaznoi.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Role;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.web.dto.client.ClientChangeDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientLoginDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.mappers.client.ClientRegisterMapper;
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

//	@Override
//	@Transactional
//	public Client findByMobileNumber(String phone) {
//		return clientRepository.findByMobileNumber(phone)
//				.orElseThrow(() ->
//						new ResourceNotFoundException("Client not found"));
//	}

	@Override
	public boolean exitAccount(String chatId) {
		try {
			Optional<Client> optionalClient = clientRepository.findByRememberToken(chatId);
			if(!optionalClient.isPresent()) {
				return false;
			}
			Client client = optionalClient.get();
			client.setRememberToken(null);
			clientRepository.save(client);
			return true; // Клиент найден, авторизован
		} catch (ResourceNotFoundException e) {
			return false; // Клиент не найден, не авторизован
		}
	}

	@Override
	@Transactional
	public Client findByRememberToken(String chatId) {
		return clientRepository.findByRememberToken(chatId)
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

	@Override
	@Transactional
	public ClientResponseDto update(ClientChangeDto clientChangeDto) {
		Optional<Client> clientOptional = clientRepository.findById(clientChangeDto.getId());
		if (clientOptional.isPresent()) {
			Client client = clientOptional.get();
			client.setMobileNumber(clientChangeDto.getMobileNumber());
			client.setFirstname(clientChangeDto.getFirstname());
			client.setMiddlename(clientChangeDto.getMiddlename());
			client.setSecondname(clientChangeDto.getSecondname());

			Client updatedClient = clientRepository.save(client);
			return clientResponseMapper.toDto(updatedClient);
		} else {
			throw new EntityNotFoundException("Client not found with id: " + clientChangeDto.getId());
		}
	}

	@Override
	@Transactional
	public void clientSaveTokenTg(String phone, String rememberToken) {
		Optional<Client> optionalClient = clientRepository.findByMobileNumber(phone);
		if (optionalClient.get() != null) {
			Client client = optionalClient.get();
			client.setRememberToken(rememberToken);
			clientRepository.save(client);
		}
	}


	@Override
	@Transactional
	public ClientResponseDto login(
			final ClientLoginDto clientLoginDto) {
		String mobileNumber = clientLoginDto.getMobileNumber();
		String password = clientLoginDto.getPassword();

		Optional<Client> clientOptional = clientRepository.findByMobileNumber(mobileNumber);
		if (clientOptional.isPresent()) {
			Client client = clientOptional.get();
			if (passwordEncoder.matches(password, client.getPassword())) {
				return clientResponseMapper.toDto(client);
			} else {
				throw new IllegalArgumentException("Неверный пароль");
			}
		} else {
			throw new IllegalArgumentException("Пользователь не найден");
		}
	}
}