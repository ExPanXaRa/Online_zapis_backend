package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.repository.UserRepository;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.web.dto.auth.UserChangePasswordDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserLoginRequestDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResetPasswordDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResponseDto;
import ru.alliedar.pokaznoi.web.mappers.UserAuthMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserAuthMapper userAuthMapper;
	private final UserRepository userRepository;


	@Override
	@Transactional
	public UserResponseDto login(
			final UserLoginRequestDto userLoginRequestDto) {
		String email = userLoginRequestDto.getEmail();
		String password = userLoginRequestDto.getPassword();

		Optional<User> userOptional = userRepository.findByUsername(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return userAuthMapper.mapToDTO(user);
			} else {
				throw new IllegalArgumentException("Неверный пароль");
			}
		} else {
			throw new IllegalArgumentException("Пользователь не найден");
		}
	}

	@Override
	@Transactional
	public boolean resetPassword(
			final UserResetPasswordDto userResetPasswordDto) {
		Optional<User> userOptional = userRepository
				.findByUsername(userResetPasswordDto.getEmail());
		return userOptional.map(user -> {
			user.setPassword(passwordEncoder.encode(userResetPasswordDto
					.getNewPassword()));
			userRepository.save(user);
			return true;
		}).orElseThrow(() -> new IllegalArgumentException(
				"Пользователь с почтой " + userResetPasswordDto.getEmail()
						+ " не существует."));
	}

	@Override
	@Transactional
	public boolean changePassword(
			final UserChangePasswordDto userChangePasswordDto) {
		String userEmail = userChangePasswordDto.getEmail();
		String oldPassword = userChangePasswordDto.getOldPassword();
		String newPassword = userChangePasswordDto.getNewPassword();

		Optional<User> userOptional = userRepository.findByUsername(userEmail);
		return userOptional.map(user -> {
			if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
				throw new IllegalArgumentException(
						"Incorrect current password");
			}
			if (oldPassword.equals(newPassword)) {
				throw new IllegalArgumentException(
						"New password must be different from the old one");
			}

			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			return true;
		}).orElseThrow(() -> new IllegalArgumentException("User with email "
				+ userEmail + " not found"));
	}


}
