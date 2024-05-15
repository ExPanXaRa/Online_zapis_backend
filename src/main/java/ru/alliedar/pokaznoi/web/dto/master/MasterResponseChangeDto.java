package ru.alliedar.pokaznoi.web.dto.master;

import lombok.Data;

@Data
public class MasterResponseChangeDto {
	private Long id;
	private String mobileNumber;
	private String email;
	private String firstname;
	private String middlename;
	private String secondname;
}
