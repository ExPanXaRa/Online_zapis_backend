package ru.alliedar.pokaznoi.web.dto.service;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;

import java.math.BigDecimal;

@Data
public class ServiceRequestDto {
	private Long id;
	private Master master;
	private String name;
	private String comment;
	private BigDecimal price;
	private String standardTime;
}
