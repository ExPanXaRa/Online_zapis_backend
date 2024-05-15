package ru.alliedar.pokaznoi.web.controller;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.web.dto.client.ClientChangeDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientLoginDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@CrossOrigin
public class ClientController {
	private final ClientService clientService;
	private final ClientRepository clientRepository;
	private final StringRedisTemplate stringRedisTemplate;

	@PostMapping("/register")
	public ResponseEntity<ClientResponseDto> registerClient(
			@Validated(OnCreate.class) final @RequestBody ClientRegisterDto clientRegisterDto) {
		ClientResponseDto newUser = clientService.create(clientRegisterDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", headers = {
					@Header(name = "Set-Cookie", description = "sessionId", schema = @Schema(type = "string"))
			})
	})
	public ResponseEntity<ClientResponseDto> loginUser(
			@Valid
			final @RequestBody ClientLoginDto clientLoginDto,
			final HttpServletResponse response,
			final HttpServletRequest request) {
		try {
			ClientResponseDto client = clientService.login(clientLoginDto);
			String key = UUID.randomUUID().toString();

			stringRedisTemplate.opsForValue()
					.set(key, "C"+String.valueOf(client.getId()));

			Cookie cookie = new Cookie("sessionId", key);
			cookie.setPath("/");
			cookie.setMaxAge(15 * 24 * 60 * 60);
			response.addCookie(cookie);
			return ResponseEntity.ok(client);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(
			final @CookieValue(name = "sessionId") String sessionId,
			final HttpServletResponse response) {
		Boolean exists = stringRedisTemplate.hasKey(sessionId);

		if (exists != null && exists) {
			stringRedisTemplate.delete(sessionId);
			Cookie cookie = new Cookie("sessionId", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/update")
	@PreAuthorize("@customSecurityExpression.canAccessClient(#clientChangeDto.id)")
	public ResponseEntity<ClientResponseDto> updateClient(@Valid @RequestBody ClientChangeDto clientChangeDto) {
		ClientResponseDto updatedClient = clientService.update(clientChangeDto);
		return ResponseEntity.ok(updatedClient);
	}


	@GetMapping
	@PreAuthorize("@customSecurityExpression.isAdmin()")
	public List<Client> getClient() {
		List<Client> client = clientRepository.findAll();
		return client;
	}
}
