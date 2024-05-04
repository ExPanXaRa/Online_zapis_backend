package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.SaleCardRepository;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.service.SaleCardService;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.SaleCardResponseDto;
import ru.alliedar.pokaznoi.web.mappers.toolsOfMaster.SaleCardResponseMapper;

@Service
@RequiredArgsConstructor
public class SaleCardServiceImpl implements SaleCardService {
	private final StringRedisTemplate stringRedisTemplate;
	private final MasterService masterService;
	private final SaleCardRepository saleCardRepository;
	private final SaleCardResponseMapper saleCardResponseMapper;

	@Override
	@Transactional
	public SaleCardResponseDto create(SaleCardRequestDto saleCardRequestDto) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key).substring(1);

		SaleCard saleCard = new SaleCard();
		saleCard.setMaster(masterService.getById(Long.valueOf(value)));
		saleCard.setName(saleCardRequestDto.getName());
		saleCard.setPercent(saleCardRequestDto.getPercent());
		saleCardRepository.save(saleCard);
		return saleCardResponseMapper.toDto(saleCard);
	}
}
