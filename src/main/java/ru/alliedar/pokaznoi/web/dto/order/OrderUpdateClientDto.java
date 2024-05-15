package ru.alliedar.pokaznoi.web.dto.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.service.Service;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderUpdateClientDto {

	@NotNull(message = "Time of start must not be null")
	private Timestamp timeOfStart;

	@Min(value = 0, message = "Rating must be greater than or equal to 0")
	@Max(value = 5, message = "Rating must be less than or equal to 5")
	private Integer rating;
	private List<Service> services;
}
