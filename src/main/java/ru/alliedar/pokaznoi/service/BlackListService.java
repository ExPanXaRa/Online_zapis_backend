package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListResponseDto;

public interface BlackListService {
	BlackListResponseDto create(BlackListRequestDto blackListRequestDto);
}
