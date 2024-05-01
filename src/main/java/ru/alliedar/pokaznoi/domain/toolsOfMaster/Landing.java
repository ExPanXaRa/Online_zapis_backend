package ru.alliedar.pokaznoi.domain.toolsOfMaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;

@Entity
@Data
@Table(name = "landings")
public class Landing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "master_id", nullable = false)
	private Master master;

	@Column(name = "description")
	private String description;

	@Column(name = "files")
	private Long files;
}

