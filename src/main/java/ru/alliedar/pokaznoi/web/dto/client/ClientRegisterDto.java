package ru.alliedar.pokaznoi.web.dto.client;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnPasswordUpdate;
import ru.alliedar.pokaznoi.web.dto.validation.OnUpdate;

@Data
public class ClientRegisterDto {
	private Long id;

	@NotBlank(message = "Number must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Pattern(regexp="\\+\\d{11}", message="Неправильный формат номера телефона")
	private String mobileNumber;

	@NotBlank(message = "Password must not be null!",
			groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be at least 8 symbols long!",
			min = 8, groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be less than 255 symbols long!",
			max = 255, groups = {OnCreate.class, OnPasswordUpdate.class})
	private String password;

	private String passwordConfirmation;

	@NotBlank(message = "Firstname must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Firstname must be less than 255 symbols long!",
			max = 255, groups = {OnCreate.class, OnUpdate.class})
	private String firstname;

	@NotBlank(message = "Middlename must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Middlename must be less than 255 symbols long!",
			max = 255, groups = {OnCreate.class, OnUpdate.class})
	private String middlename;

	@NotBlank(message = "Secondname must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Secondname must be less than 255 symbols long!",
			max = 255, groups = {OnCreate.class, OnUpdate.class})
	private String secondname;
	@AssertTrue(message = "Password and confirmation must match!",
			groups = {OnCreate.class, OnPasswordUpdate.class})
	public boolean isPasswordConfirmed() {
		return password != null && password.equals(passwordConfirmation);
	}
}
