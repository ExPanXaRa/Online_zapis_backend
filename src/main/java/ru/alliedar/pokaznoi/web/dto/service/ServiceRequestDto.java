package ru.alliedar.pokaznoi.web.dto.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnUpdate;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class ServiceRequestDto {
	private Long id;
	@NotBlank(message = "Id must be not null!")
	private Long master_id;
	private Master master;

	@NotBlank(message = "Name must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Name must be less than 255 symbols long!",
			max = 255, groups = {OnCreate.class, OnUpdate.class})
	private String name;

	@NotBlank(message = "Comment must not be null!",
			groups = {OnCreate.class, OnUpdate.class})
	@Length(message = "Comment must be less than 255 symbols long!",
			max = 255, groups = {OnCreate.class, OnUpdate.class})
	private String comment;

	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
	private BigDecimal price;

	private Time standardTime;
}
