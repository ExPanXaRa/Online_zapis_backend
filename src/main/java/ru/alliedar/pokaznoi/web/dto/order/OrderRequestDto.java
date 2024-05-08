package ru.alliedar.pokaznoi.web.dto.order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.service.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderRequestDto {
	@NotNull(message = "Id must not be null")
	private Long id;

	@NotNull(message = "Client Id must not be null")
	@Positive(message = "Client Id must be positive")
	private Long client_id;

	@NotNull(message = "Master Id must not be null")
	@Positive(message = "Master Id must be positive")
	private Long master_id;

	@NotNull(message = "Price must not be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private BigDecimal price;

	@NotNull(message = "Time of start must not be null")
	private Timestamp timeOfCreate;

	private Timestamp timeOfStart;
	private Timestamp timeOfEnd;
	private Integer rating;
	private List<Service> services;
}
