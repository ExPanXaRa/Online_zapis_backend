package ru.alliedar.pokaznoi.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnPasswordUpdate;

@Data
public class UserChangePasswordDto {
	private String email;
	private String oldPassword;

	@NotBlank(message = "Password must not be null!", groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be at least 8 symbols long!", min = 8,
			groups = {OnCreate.class, OnPasswordUpdate.class})
	@Length(message = "Password must be less than 255 symbols long!", max = 255,
			groups = {OnCreate.class, OnPasswordUpdate.class})
	private String newPassword;

}
