package ru.alan.viewPerson.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alan.viewPerson.dto.user.*;
import ru.alan.viewPerson.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;
	private final StringRedisTemplate stringRedisTemplate;

	public AuthController(UserService userService, StringRedisTemplate stringRedisTemplate) {
		this.userService = userService;
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
		UserResponseDto newUser = userService.create(userRequestDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response, HttpServletRequest request) {
		try {
			UserResponseDto user = userService.login(userLoginRequestDto);
			String key = UUID.randomUUID().toString();

			stringRedisTemplate.opsForValue().set(key, String.valueOf(user.getId()));

			Cookie cookie = new Cookie("sessionId", key);
			cookie.setPath("/");
			cookie.setMaxAge(15 * 24 * 60 * 60);
			response.addCookie(cookie);
			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@CookieValue(name = "sessionId") String sessionId) {
		Boolean exists = stringRedisTemplate.hasKey(sessionId);

		if (exists != null && exists) {
			stringRedisTemplate.delete(sessionId);
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody UserResetPasswordDto userResetPasswordDto) {
		if (userService.resetPassword(userResetPasswordDto)) {
			return ResponseEntity.ok("Пароль успешно сброшен");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с указанным адресом электронной почты не найден");

	}

	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@CookieValue(name = "sessionId") String sessionId,
												 HttpServletRequest request, HttpServletResponse response,
												 @RequestBody UserChangePasswordDto userChangePasswordDto) {
		try {
			Boolean exists = stringRedisTemplate.hasKey(sessionId);

			if (exists != null && exists) {
				if (userService.changePassword(userChangePasswordDto)) {
					return ResponseEntity.ok("Пароль успешно изменен");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с указанным адресом электронной почты не найден");
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Недействительная сессия");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при изменении пароля: " + e.getMessage());
		}
	}

}

