package ru.alliedar.pokaznoi.web.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnUpdate;

@Data
public class ClientChangeDto {
	private Long id;

	@NotBlank(message = "Number must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Pattern(regexp="\\+\\d{11}", message="Неправильный формат номера телефона")
	private String mobileNumber;

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
}
