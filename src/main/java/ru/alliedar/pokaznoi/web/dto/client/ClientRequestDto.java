package ru.alliedar.pokaznoi.web.dto.client;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ClientRequestDto implements Serializable {
	private Long id;
	private String mobileNumber;
	private String password;
	private String firstname;
	private String middlename;
	private String secondname;
	private Timestamp createdAt;
}
