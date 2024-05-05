package ru.alliedar.pokaznoi.web.dto.toolsOfMaster;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.alliedar.pokaznoi.domain.master.Master;

@Data
public class BlackListRequestDto {
	private Long id;
	@NotBlank(message = "master_id must be not null!")
	private Long master_id;
	@NotBlank(message = "clientPhones must be not null!")
	private String clientPhones;
}
