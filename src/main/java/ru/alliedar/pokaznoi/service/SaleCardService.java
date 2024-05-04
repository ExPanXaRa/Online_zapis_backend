package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardResponseDto;
import ru.alliedar.pokaznoi.web.mappers.toolsOfMaster.SaleCardResponseMapper;

public interface SaleCardService {
	SaleCardResponseDto create(SaleCardRequestDto saleCardRequestDto);
}
