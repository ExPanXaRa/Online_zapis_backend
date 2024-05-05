package ru.alliedar.pokaznoi.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.ClientRepository;
import ru.alliedar.pokaznoi.repository.SaleCardRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.service.SaleCardService;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardResponseDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardUpdateDto;
import ru.alliedar.pokaznoi.web.mappers.toolsOfMaster.SaleCardResponseMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleCardServiceImpl implements SaleCardService {
	private final StringRedisTemplate stringRedisTemplate;
	private final MasterService masterService;
	private final SaleCardRepository saleCardRepository;
	private final SaleCardResponseMapper saleCardResponseMapper;
	private final ClientService clientService;
	private final ClientRepository clientRepository;

	@Override
	public SaleCardResponseDto create(SaleCardRequestDto saleCardRequestDto) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key).substring(1);

		SaleCard saleCard = new SaleCard();
		saleCard.setMaster(masterService.getById(Long.valueOf(value)));
		saleCard.setName(saleCardRequestDto.getName());
		saleCard.setPercent(saleCardRequestDto.getPercent());
//		clientService.getById(saleCardRequestDto.getClient_id()).addSaleCard(saleCard);
		saleCardRepository.save(saleCard);
		return saleCardResponseMapper.toDto(saleCard);
	}

	@Override
	public SaleCardResponseDto update(SaleCardUpdateDto saleCardUpdateDto, Long id) {
		SaleCard saleCard = saleCardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("SaleCard not found with id: " + id));

		saleCard.setName(saleCardUpdateDto.getName());
		saleCard.setPercent(saleCardUpdateDto.getPercent());

//		if (saleCardUpdateDto.getClients() != null) {
//			saleCard.setClients(saleCardUpdateDto.getClients());
//		}

		SaleCard updatedSaleCard = saleCardRepository.save(saleCard);
		return saleCardResponseMapper.toDto(updatedSaleCard);
	}

	@Override
	public SaleCardResponseDto removeClient(Long saleCardId, Long clientId) {
		// Получить объект SaleCard из репозитория по ID
		SaleCard saleCard = saleCardRepository.findById(saleCardId)
				.orElseThrow(() -> new EntityNotFoundException("SaleCard not found with id: " + saleCardId));

		// Получить объект Client из репозитория по ID
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));

		saleCard.getClients().remove(client);
		clientService.getById(clientId).removeSaleCard(saleCard);
		SaleCard updatedSaleCard = saleCardRepository.save(saleCard);
		return saleCardResponseMapper.toDto(updatedSaleCard);
	}

	@Override
	public SaleCardResponseDto addClient(Long saleCardId, Long clientId) {
		SaleCard saleCard = saleCardRepository.findById(saleCardId)
				.orElseThrow(() -> new EntityNotFoundException("SaleCard not found with id: " + saleCardId));

		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));

		if (saleCard.getClients().contains(client)) {
			throw new IllegalStateException("Client with id " + clientId + " is already associated with SaleCard with id " + saleCardId);
		}

		saleCard.getClients().add(client);
		client.addSaleCard(saleCard);
		SaleCard updatedSaleCard = saleCardRepository.save(saleCard);
		return saleCardResponseMapper.toDto(updatedSaleCard);
	}



}
