package ru.alliedar.pokaznoi.web.dto.client;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnPasswordUpdate;

@Data
public class ClientRegisterDto {
	private Long id;
	private String mobileNumber;
	private String password;
	private String passwordConfirmation;
	private String firstname;
	private String middlename;
	private String secondname;
	@AssertTrue(message = "Password and confirmation must match!",
			groups = {OnCreate.class, OnPasswordUpdate.class})
	public boolean isPasswordConfirmed() {
		return password != null && password.equals(passwordConfirmation);
	}
}
