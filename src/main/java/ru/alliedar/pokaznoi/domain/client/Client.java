package ru.alliedar.pokaznoi.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role")
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Column(name = "mobile_number", nullable = false)
	private String mobileNumber;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "firstname", nullable = false)
	private String firstname;

	@Column(name = "middlename")
	private String middlename;

	@Column(name = "secondname", nullable = false)
	private String secondname;

	@Column(name = "remember_token")
	private String rememberToken;

	@Column(name = "telegram_token")
	private String telegramToken;

	@Column(name = "created_at", nullable = false)
	private Timestamp createdAt;

	@ManyToMany
	@JoinTable(
			name = "clients_sale_cards",
			joinColumns = @JoinColumn(name = "client_id"),
			inverseJoinColumns = @JoinColumn(name = "sale_card_id")
	)
	@JsonIgnoreProperties("clients")
	private List<SaleCard> saleCards = new ArrayList<>();

	@OneToMany(mappedBy = "client")
	@JsonIgnoreProperties("client")
	private List<Order> orders;

	public void addSaleCard(SaleCard saleCard){
		this.saleCards.add(saleCard);
		if (saleCard.getClients() != null) {
			saleCard.getClients().add(this);
		} else {
			saleCard.setClients(List.of(this));
		}
	}

	public void removeSaleCard(SaleCard saleCard){
		this.saleCards.remove(saleCard);
		saleCard.getClients().remove(this);
	}
}
