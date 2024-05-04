package ru.alliedar.pokaznoi.web.dto.service;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;

import java.math.BigDecimal;
import java.sql.Time;

@Data
public class ServiceResponseDto {
	private Long id;
	private Master master;
	private String name;
	private String comment;
	private BigDecimal price;
	private Time standardTime;
}
