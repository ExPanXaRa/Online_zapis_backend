package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.repository.SaleCardRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.web.dto.auth.UserRequestDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResponseDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.mappers.ClientRequestMapper;
import ru.alliedar.pokaznoi.web.mappers.ClientResponseMapper;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
	private final ClientService clientService;
	private final ClientRepository clientRepository;

	@PostMapping("/register")
	public ResponseEntity<ClientResponseDto> registerClient(
			final @RequestBody ClientRequestDto clientRequestDto) {
		ClientResponseDto newUser = clientService.create(clientRequestDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}


	@GetMapping("/")
	public List<Client> getClient() {
		List<Client> client = clientRepository.findAll();
		return client;
	}
}
