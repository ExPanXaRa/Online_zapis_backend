package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.ServiceRepository;
import ru.alliedar.pokaznoi.service.ServiceService;
import ru.alliedar.pokaznoi.web.dto.service.ServiceRequestDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {
	private final ServiceRepository serviceRepository;
	private final ServiceService serviceService;

	@PostMapping("/create")
//    @PreAuthorize("@customSecurityExpression.canAccessMaster(#blackListRequestDto.master_id)")
	public ResponseEntity<ServiceResponseDto> create(final @RequestBody ServiceRequestDto serviceRequestDto) {
		ServiceResponseDto serviceResponseDto = serviceService.create(serviceRequestDto);
		return new ResponseEntity<>(serviceResponseDto, HttpStatus.CREATED);

	}

	@GetMapping
	@PreAuthorize("@customSecurityExpression.canAccessMaster(#id)")
	public List<Service> getServices() {
		List<Service> service = serviceRepository.findAll();
		return service;
	}
}
