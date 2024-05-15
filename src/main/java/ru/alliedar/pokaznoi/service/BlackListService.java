package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListResponseDto;

import java.util.List;

public interface BlackListService {
	boolean isBlackListOwner(Long userId, Long blackListId);
	List<BlackListResponseDto> getAll();
	BlackListResponseDto create(BlackListRequestDto blackListRequestDto);
	BlackListResponseDto update(Long id, BlackListRequestDto blackListRequestDto);
	void delete(Long id);
}
