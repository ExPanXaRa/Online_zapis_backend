package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
	private final ClientService clientService;
	private final ClientRepository clientRepository;

	@PostMapping("/register")
	public ResponseEntity<ClientResponseDto> registerClient(
			@Validated(OnCreate.class) final @RequestBody ClientRegisterDto clientRegisterDto) {
		ClientResponseDto newUser = clientService.create(clientRegisterDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}


	@GetMapping
	public List<Client> getClient() {
		List<Client> client = clientRepository.findAll();
		return client;
	}
}
