package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.master.MasterRegisterDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRequestDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseDto;

public interface MasterService {
	MasterResponseDto create(MasterRegisterDto masterRegisterDto);
}
