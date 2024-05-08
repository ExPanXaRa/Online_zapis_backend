package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.repository.ServiceRepository;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.service.ServiceService;
import ru.alliedar.pokaznoi.web.dto.service.ServiceRequestDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;
import ru.alliedar.pokaznoi.web.mappers.service.ServiceRequestMapper;
import ru.alliedar.pokaznoi.web.mappers.service.ServiceResponseMapper;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
	private final StringRedisTemplate stringRedisTemplate;
	private final MasterService masterService;
	private final ServiceRepository serviceRepository;
	private final ServiceResponseMapper serviceResponseMapper;
	private final ServiceRequestMapper serviceRequestMapper;

	@Override
	public boolean isServiceOwner(Long userId, Long serviceId) {
		return serviceRepository.isServiceOwner(userId, serviceId);
	}

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

	@Override
	public List<ServiceResponseDto> getAll() {
		List<Service> services = serviceRepository.findAll();
		return serviceResponseMapper.toDto(services);
	}

	@Override
	public ServiceResponseDto getById(Long id) {
		Service service =  serviceRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException("User not found."));
		return serviceResponseMapper.toDto(service);
	}



	@Override
	public ServiceResponseDto update(ServiceRequestDto serviceRequestDto, Long id) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key).substring(1);
		serviceRequestDto.setId(id);
		Service service = serviceRequestMapper.toEntity(serviceRequestDto);
		service.setMaster(masterService.getById(Long.valueOf(value)));
		serviceRepository.save(service);
		return serviceResponseMapper.toDto(service);
	}

	@Override
	public void delete(Long id) {
		serviceRepository.deleteById(id);
	}


}
