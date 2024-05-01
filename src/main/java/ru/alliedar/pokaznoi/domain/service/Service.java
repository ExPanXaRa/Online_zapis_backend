package ru.alliedar.pokaznoi.domain.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Data
@Table(name = "services")
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "master_id", nullable = false)
	private Master master;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "comment")
	private String comment;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "standard_time")
	private String standardTime;

	@ManyToMany(mappedBy = "services")
	@JsonIgnoreProperties("services")
	List<Order> orders;

}

