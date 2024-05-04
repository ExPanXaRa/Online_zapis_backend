package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.SaleCardRepository;
import ru.alliedar.pokaznoi.service.SaleCardService;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListResponseDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saleCards")
@RequiredArgsConstructor
public class SaleCardController {
	private final SaleCardRepository saleCardRepository;
	private final SaleCardService saleCardService;

	@PostMapping("/create")
//    @PreAuthorize("@customSecurityExpression.canAccessMaster(#blackListRequestDto.master_id)")
	public ResponseEntity<SaleCardResponseDto> create(final @RequestBody SaleCardRequestDto saleCardRequestDto) { // TODO нормально сделать надо создание
		SaleCardResponseDto saleCardResponseDto = saleCardService.create(saleCardRequestDto);
		return new ResponseEntity<>(saleCardResponseDto, HttpStatus.CREATED);

	}


	@GetMapping
	public List<SaleCard> getSaleCards() {
		List<SaleCard> card = saleCardRepository.findAll();
		return card;
	}
}
