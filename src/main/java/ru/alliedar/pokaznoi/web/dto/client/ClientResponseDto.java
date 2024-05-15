package ru.alliedar.pokaznoi.web.dto.client;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
public class ClientResponseDto implements Serializable {
	private Long id;
	private String mobileNumber;
	private String firstname;
	private String middlename;
	private String secondname;
	private Timestamp createdAt;
	private Set<SaleCard> saleCards;
}
