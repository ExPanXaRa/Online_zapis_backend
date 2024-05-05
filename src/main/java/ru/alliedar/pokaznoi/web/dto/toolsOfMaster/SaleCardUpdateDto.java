package ru.alliedar.pokaznoi.web.dto.toolsOfMaster;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnUpdate;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SaleCardUpdateDto {
//	private List<Client> clients;

	@NotBlank(message = "Name must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Name must be less than 255 symbols long!",
			max = 255, groups = {OnCreate.class, OnUpdate.class})
	private String name;

	@NotNull(message = "Discount percentage is required")
	@Min(value = 0, message = "Discount percentage must be greater than or equal to 0")
	@Max(value = 100, message = "Discount percentage must be less than or equal to 100")
	private BigDecimal percent;
}