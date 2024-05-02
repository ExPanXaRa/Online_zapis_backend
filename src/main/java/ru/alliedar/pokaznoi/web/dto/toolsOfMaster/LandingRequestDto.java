package ru.alliedar.pokaznoi.web.dto.toolsOfMaster;

import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;

@Data
public class LandingRequestDto {
	private Long id;
	private Master master;
	private String description;
	private Long files;
}
