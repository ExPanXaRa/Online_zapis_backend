package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;
import ru.alliedar.pokaznoi.repository.BlackListRepository;
import ru.alliedar.pokaznoi.service.BlackListService;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListResponseDto;
import ru.alliedar.pokaznoi.web.mappers.toolsOfMaster.BlackListResponseMapper;

@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {
	private final StringRedisTemplate stringRedisTemplate;
	private final BlackListRepository blackListRepository;
	private final BlackListResponseMapper blackListResponseMapper;
	private final MasterService masterService;

	@Override
	@Transactional
	public BlackListResponseDto create(BlackListRequestDto blackListRequestDto) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key).substring(1);

		BlackList blackList = new BlackList();
		blackList.setMaster(masterService.getById(Long.valueOf(value)));
		blackList.setClientPhones(blackListRequestDto.getClientPhones());
		blackListRepository.save(blackList);
		return blackListResponseMapper.toDto(blackList);
	}
}
