package ru.alliedar.pokaznoi.domain.toolsOfMaster;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "sale_cards")
public class SaleCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "master_id", nullable = false)
	@JsonIgnoreProperties({"master", "orders", "saleCards", "services","blackLists"})
	private Master master;

	@Column(name = "name")
	private String name;

	@Column(name = "percent", nullable = false)
	private BigDecimal percent;

	@ManyToMany(mappedBy = "saleCards")
	@JsonIgnoreProperties({"saleCards","orders"})
	private List<Client> clients;

}


