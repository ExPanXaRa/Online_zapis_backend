package ru.alliedar.pokaznoi.domain.master;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.order.Order;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "masters")
public class Master {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mobile_number", nullable = false)
	private String mobileNumber;

	@Column(name = "email")
	private String email;

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

	@OneToMany(mappedBy = "master")
	@JsonIgnoreProperties("master")
	private List<Order> orders;
}

