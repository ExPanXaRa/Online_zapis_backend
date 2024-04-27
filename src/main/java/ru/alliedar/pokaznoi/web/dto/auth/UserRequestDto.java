package ru.alliedar.pokaznoi.web.dto.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnUpdate;
import ru.alliedar.pokaznoi.web.dto.validation.OnPasswordUpdate;
import java.io.Serializable;

@Data
public class UserRequestDto implements Serializable {

	private Long id;

	@NotBlank(message = "Email must not be null!", groups = {OnCreate.class, OnUpdate.class})
	@Email(message = "Email is not valid!", groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Email must be less than 255 symbols long!", max = 255,
			groups = {OnCreate.class, OnUpdate.class})
	private String email;

	@NotBlank(message = "Firstname must not be null!", groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Firstname must be less than 255 symbols long!", max = 255,
			groups = {OnCreate.class, OnUpdate.class})
	private String login;

	@NotBlank(message = "Password must not be null!", groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be at least 8 symbols long!", min = 8,
			groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be less than 255 symbols long!", max = 255,
			groups = {OnCreate.class, OnPasswordUpdate.class})
	private String password;

	@NotBlank(message = "Confirmation must not be null!", groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Confirmation must be less than 255 symbols long!", max = 255,
			groups = {OnCreate.class, OnPasswordUpdate.class})
	private String passwordConfirmation;

	@AssertTrue(message = "Password and confirmation must match!", groups = {OnCreate.class, OnPasswordUpdate.class})
	public boolean isPasswordConfirmed() {
		return password != null && password.equals(passwordConfirmation);
	}

}
