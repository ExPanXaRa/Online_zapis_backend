package ru.alliedar.pokaznoi.service.impl;

import jakarta.persistence.EntityNotFoundException;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {
	private final StringRedisTemplate stringRedisTemplate;
	private final BlackListRepository blackListRepository;
	private final BlackListResponseMapper blackListResponseMapper;
	private final MasterService masterService;

	@Override
	@Transactional(readOnly = true)
	public List<BlackListResponseDto> getAll() {
		List<BlackList> blackLists = blackListRepository.findAll();
		return blackLists.stream()
				.map(blackListResponseMapper::toDto)
				.collect(Collectors.toList());
	}

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

	@Override
	@Transactional
	public BlackListResponseDto update(Long id, BlackListRequestDto blackListRequestDto) {
		BlackList blackList = blackListRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("BlackList not found with id: " + id));

		blackList.setClientPhones(blackListRequestDto.getClientPhones());

		BlackList updatedBlackList = blackListRepository.save(blackList);
		return blackListResponseMapper.toDto(updatedBlackList);
	}
	@Override
	public boolean isBlackListOwner(Long userId, Long blackListId) {
		return blackListRepository.isBlackListOwner(userId, blackListId);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		BlackList blackList = blackListRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("BlackList not found with id: " + id));
		blackListRepository.delete(blackList);
	}
}
