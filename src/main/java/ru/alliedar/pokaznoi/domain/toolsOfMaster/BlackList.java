package ru.alliedar.pokaznoi.domain.toolsOfMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;

@Entity
@Data
@Table(name = "black_lists")
public class BlackList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "master_id", nullable = false)
	private Master master;

	@Column(name = "client_phones")
	private String clientPhones;

}
