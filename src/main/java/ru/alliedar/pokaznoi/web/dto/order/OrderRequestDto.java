package ru.alliedar.pokaznoi.web.dto.order;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderRequestDto {
	private Long id;
	private Client client;
	private Master master;
	private BigDecimal price;
	private Timestamp timeOfCreate;
	private Timestamp timeOfStart;
	private Timestamp timeOfEnd;
	private Integer rating;
}
