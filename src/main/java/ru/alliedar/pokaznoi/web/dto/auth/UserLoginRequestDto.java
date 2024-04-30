package ru.alliedar.pokaznoi.web.dto.auth;

import lombok.Data;

@Data
public class UserLoginRequestDto {

	private String email;
	private String password;

}
