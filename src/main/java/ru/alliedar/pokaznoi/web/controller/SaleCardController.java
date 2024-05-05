package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardUpdateDto;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;

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

	@PostMapping("/update/{id}")
	public ResponseEntity<SaleCardResponseDto> update(@PathVariable Long id,
													  @Validated(OnCreate.class)
													  final @RequestBody SaleCardUpdateDto saleCardUpdateDto) {
		SaleCardResponseDto saleCardResponseDto = saleCardService.update(saleCardUpdateDto, id);
		return new ResponseEntity<>(saleCardResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/{saleCardId}/clients/{clientId}")
	public ResponseEntity<SaleCardResponseDto> removeClientFromSaleCard(@PathVariable Long saleCardId,
																		@PathVariable Long clientId) {
		SaleCardResponseDto updatedSaleCard = saleCardService.removeClient(saleCardId, clientId);
		return ResponseEntity.ok(updatedSaleCard);
	}

	@PostMapping("/{saleCardId}/clients/{clientId}")
	public ResponseEntity<SaleCardResponseDto> addClientToSaleCard(@PathVariable Long saleCardId,
																   @PathVariable Long clientId) {
		SaleCardResponseDto updatedSaleCard = saleCardService.addClient(saleCardId, clientId);
		return ResponseEntity.ok(updatedSaleCard);
	}


	@GetMapping()
	public List<SaleCard> getSaleCards() {
		List<SaleCard> card = saleCardRepository.findAll();
		return card;
	}
}
