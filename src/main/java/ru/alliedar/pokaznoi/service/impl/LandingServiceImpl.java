//package ru.alliedar.pokaznoi.service.impl;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.transaction.annotation.Transactional;
//import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
//import ru.alliedar.pokaznoi.service.LandingService;
//import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.LandingRequestDto;
//import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.LandingResponseDto;
//
//
//public class LandingServiceImpl implements LandingService {
//	@Override
//	@Transactional
//	public LandingResponseDto create(LandingRequestDto landingRequestDto) {
//		Authentication authentication =
//				SecurityContextHolder.getContext().getAuthentication();
//		String key = (String) authentication.getPrincipal();
//		String value = stringRedisTemplate.opsForValue().get(key).substring(1);
//
//		SaleCard saleCard = new SaleCard();
//		saleCard.setMaster(masterService.getById(Long.valueOf(value)));
//		saleCard.setName(saleCardRequestDto.getName());
//		saleCard.setPercent(saleCardRequestDto.getPercent());
//		saleCardRepository.save(saleCard);
//		return saleCardResponseMapper.toDto(saleCard);
//	}
//}
