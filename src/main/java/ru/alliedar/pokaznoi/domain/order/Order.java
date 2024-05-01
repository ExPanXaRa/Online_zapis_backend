package ru.alliedar.pokaznoi.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	@JsonIgnoreProperties({"saleCards", "orders"})
	private Client client;

	@ManyToOne
	@JoinColumn(name = "master_id", nullable = false)
	@JsonIgnoreProperties("orders")
	private Master master;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "time_of_create", nullable = false)
	private Timestamp timeOfCreate;

	@Column(name = "time_of_start", nullable = false)
	private Timestamp timeOfStart;

	@Column(name = "time_of_end", nullable = false)
	private Timestamp timeOfEnd;

	@Column(name = "rating")
	private Integer rating;

	@ManyToMany
	@JoinTable(
			name = "orders_services",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "service_id")
	)
	@JsonIgnoreProperties("orders")
	List<Service> services;
}

