package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.web.dto.client.ClientChangeDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientLoginDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterChangeDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterLoginDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRegisterDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRequestDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseChangeDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseDto;

public interface MasterService {
	MasterResponseDto create(MasterRegisterDto masterRegisterDto);
	MasterResponseDto login(MasterLoginDto MasterLoginDto);
	Master getById(final Long id);

	MasterResponseChangeDto update(MasterChangeDto masterChangeDto);

	boolean isBlackListOwner(Long userId, Long blackListId);

	boolean isSaleCardOwner(Long userId, Long blackListId);
	boolean isServiceOwner(Long userId, Long blackListId);

}
