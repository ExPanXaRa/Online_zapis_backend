package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListResponseDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardResponseDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardUpdateDto;
import ru.alliedar.pokaznoi.web.mappers.toolsOfMaster.SaleCardResponseMapper;

import java.math.BigDecimal;
import java.util.List;

public interface SaleCardService {
	boolean isSaleCardOwner(Long userId, Long saleCardId);
	BigDecimal clientHasSale(Long clientId, Long masterId);
	List<SaleCardResponseDto> getAll();
	SaleCardResponseDto create(SaleCardRequestDto saleCardRequestDto);
	SaleCardResponseDto update(SaleCardUpdateDto saleCardUpdateDto, Long id);
	SaleCardResponseDto addClient(Long saleCardId, Long clientId);
	SaleCardResponseDto removeClient(Long saleCardId, Long clientId);

}
