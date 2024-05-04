package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.repository.ServiceRepository;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.service.ServiceService;
import ru.alliedar.pokaznoi.web.dto.service.ServiceRequestDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;
import ru.alliedar.pokaznoi.web.mappers.service.ServiceResponseMapper;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
	private final StringRedisTemplate stringRedisTemplate;
	private final MasterService masterService;
	private final ServiceRepository serviceRepository;
	private final ServiceResponseMapper serviceResponseMapper;

	@Override
	@Transactional
	public ServiceResponseDto create(ServiceRequestDto serviceRequestDto) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key).substring(1);

		Service service = new Service();
		service.setMaster(masterService.getById(Long.valueOf(value)));
		service.setName(serviceRequestDto.getName());
		service.setComment(serviceRequestDto.getComment());
		service.setPrice(serviceRequestDto.getPrice());
		service.setStandardTime(serviceRequestDto.getStandardTime());
		serviceRepository.save(service);
		return serviceResponseMapper.toDto(service);
	}
}
