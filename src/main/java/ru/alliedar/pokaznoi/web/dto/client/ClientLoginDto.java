package ru.alliedar.pokaznoi.web.dto.client;

import lombok.Data;

@Data
public class ClientLoginDto {
	private String mobileNumber;
	private String password;
}
