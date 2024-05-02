package ru.alliedar.pokaznoi.web.dto.toolsOfMaster;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SaleCardResponseDto {
	private Long id;
	private Master master;
	private String name;
	private BigDecimal percent;
	private List<Client> clients;
}
