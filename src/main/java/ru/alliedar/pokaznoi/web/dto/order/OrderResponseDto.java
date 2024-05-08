package ru.alliedar.pokaznoi.web.dto.order;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.service.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderResponseDto {
	private Long id;
	private Client client;
	private Master master;
	private BigDecimal price;
	private Timestamp timeOfCreate;
	private Timestamp timeOfStart;
	private Timestamp timeOfEnd;
	private Integer rating;
	private List<Service> services;
}
