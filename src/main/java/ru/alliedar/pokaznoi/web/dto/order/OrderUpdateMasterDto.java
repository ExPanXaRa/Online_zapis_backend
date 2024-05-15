package ru.alliedar.pokaznoi.web.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.service.Service;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderUpdateMasterDto {

	@NotNull(message = "Time of start must not be null")
	private Timestamp timeOfStart;
	private List<Service> services;
}
