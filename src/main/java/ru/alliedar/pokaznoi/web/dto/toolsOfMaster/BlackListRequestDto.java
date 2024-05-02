package ru.alliedar.pokaznoi.web.dto.toolsOfMaster;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;

@Data
public class BlackListRequestDto {
	private Long id;
	private Master master;
	private String clientPhones;
}
