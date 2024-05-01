package ru.alliedar.pokaznoi.domain.client;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.task.Task;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	private List<SaleCard> saleCards;
}
