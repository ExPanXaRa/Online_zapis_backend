package ru.alliedar.pokaznoi.domain.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
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
	@JsonIgnoreProperties({"orders","saleCards","blackLists","services"})
	private Master master;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "comment")
	private String comment;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "standard_time")
	private Time standardTime;

	@ManyToMany(mappedBy = "services")
	@JsonIgnoreProperties("services")
	List<Order> orders;

	public void addOrders(Order order){
		if (this.orders != null) {
			this.orders.add(order);
		} else {
			this.setOrders(List.of(order));
		}

		if (order.getServices() != null) {
			order.getServices().add(this);
		} else {
			order.setServices(new ArrayList<>(List.of(this)));
		}
	}

	public void removeOrders(Order order){
		this.orders.remove(order);
		order.getServices().remove(this);
	}
}

