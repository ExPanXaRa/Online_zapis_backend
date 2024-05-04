package ru.alliedar.pokaznoi.web.dto.master;


import lombok.Data;

@Data
public class MasterRequestDto {
	private Long id;
	private String mobileNumber;
	private String email;
	private String password;
	private String firstname;
	private String middlename;
	private String secondname;
	private String rememberToken;
	private String telegramToken;
}
