package ru.alan.viewPerson.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import ru.alan.viewPerson.dto.user.*;
import ru.alan.viewPerson.entity.UserEntity;

import java.util.Optional;

public interface UserService {
	@Transactional(readOnly = true)
	UserDetails loadUserByUsername(String login);

	UserResponseDto create(UserRequestDto userRequestDto);
	UserResponseDto login(UserLoginRequestDto userLoginRequestDto);
	boolean resetPassword(UserResetPasswordDto userResetPasswordDto);
	boolean changePassword(UserChangePasswordDto userChangePasswordDto);

}
