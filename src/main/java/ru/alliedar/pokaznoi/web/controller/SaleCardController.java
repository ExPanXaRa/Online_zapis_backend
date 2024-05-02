package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.SaleCardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saleCards")
@RequiredArgsConstructor
public class SaleCardController {
	private final SaleCardRepository saleCardRepository;


	@GetMapping
	public List<SaleCard> getSaleCards() {
		List<SaleCard> card = saleCardRepository.findAll();
		return card;
	}
}
