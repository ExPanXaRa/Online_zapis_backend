package ru.alliedar.pokaznoi.web.dto.master;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Landing;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.domain.user.Role;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MasterResponseDto {
	private Long id;
	private String mobileNumber;
	private String email;
	private String firstname;
	private String middlename;
	private String secondname;
	private String rememberToken;
	private String telegramToken;
	private Timestamp createdAt;
	private List<Order> orders;
	private List<SaleCard> saleCards;
	private List<BlackList> blackLists;
	private List<Service> services;
	private Landing landing;
}
