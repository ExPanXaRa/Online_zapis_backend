package ru.alliedar.pokaznoi.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Role;
import ru.alliedar.pokaznoi.repository.MasterRepository;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.web.dto.master.MasterChangeDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterLoginDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRegisterDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseChangeDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseDto;
import ru.alliedar.pokaznoi.web.mappers.master.MasterRegisterMapper;
import ru.alliedar.pokaznoi.web.mappers.master.MasterResponseChangeMapper;
import ru.alliedar.pokaznoi.web.mappers.master.MasterResponseMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl  implements MasterService {

	private final MasterRepository masterRepository;
	private final MasterRegisterMapper masterRegisterMapper;
	private final MasterResponseMapper masterResponseMapper;
	private final PasswordEncoder passwordEncoder;
	private final MasterResponseChangeMapper masterResponseChangeMapper;

	@Override
	@Transactional
	public MasterResponseDto create(MasterRegisterDto masterRegisterDto) {
		Optional<Master> masterNumber =
				masterRepository.findByMobileNumber(masterRegisterDto.getMobileNumber());
		Optional<Master> masterEmail =
				masterRepository.findByEmail(masterRegisterDto.getEmail());

		if (masterNumber.isPresent()) {
			throw new IllegalArgumentException("Пользователь с номером "
					+ masterRegisterDto.getMobileNumber() + " уже существует.");
		}
		if (masterEmail.isPresent()) {
			throw new IllegalArgumentException("Пользователь с почтой "
					+ masterRegisterDto.getEmail() + " уже существует.");
		}

		if (!masterRegisterDto.isPasswordConfirmed()) {
			throw new IllegalArgumentException("Пароли не равны");
		}
		masterRegisterDto.setPassword(passwordEncoder.encode(masterRegisterDto.getPassword()));

		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT+6"));

		Timestamp timestamp = Timestamp.valueOf(localDateTime);

		Master master = masterRegisterMapper.toEntity(masterRegisterDto);
		master.setCreatedAt(timestamp);
		master.setRole(Role.ROLE_MASTER);
		masterRepository.save(master);
		return masterResponseMapper.toDto(master);
	}

	@Override
	@Transactional
	public MasterResponseChangeDto update(MasterChangeDto masterChangeDto) {
		Optional<Master> masterOptional = masterRepository.findById(masterChangeDto.getId());
		if (masterOptional.isPresent()) {
			Master master = masterOptional.get();
			master.setEmail(masterChangeDto.getEmail());
			master.setMobileNumber(masterChangeDto.getMobileNumber());
			master.setFirstname(masterChangeDto.getFirstname());
			master.setMiddlename(masterChangeDto.getMiddlename());
			master.setSecondname(masterChangeDto.getSecondname());

			Master updatedMaster = masterRepository.save(master);
			return masterResponseChangeMapper.toDto(updatedMaster);
		} else {
			throw new EntityNotFoundException("Client not found with id: " + masterChangeDto.getId());
		}
	}

	@Override
	public boolean isBlackListOwner(Long userId, Long blackListId) {
		return masterRepository.isBlackListOwner(userId, blackListId);
	}

	@Override
	public boolean isSaleCardOwner(Long userId, Long saleCardId) {
		return masterRepository.isSaleCardOwner(userId, saleCardId);
	}

	@Override
	public boolean isServiceOwner(Long userId, Long serviceId) {
		return masterRepository.isServiceOwner(userId, serviceId);
	}


	@Override
	@Transactional
	public MasterResponseDto login(MasterLoginDto masterLoginDto) {
		String mobileNumber = masterLoginDto.getMobileNumber();
		String password = masterLoginDto.getPassword();

		Optional<Master> masterOptional = masterRepository.findByMobileNumber(mobileNumber);
		if (masterOptional.isPresent()) {
			Master master = masterOptional.get();
			if (passwordEncoder.matches(password, master.getPassword())) {
				return masterResponseMapper.toDto(master);
			} else {
				throw new IllegalArgumentException("Неверный пароль");
			}
		} else {
			throw new IllegalArgumentException("Пользователь не найден");
		}
	}

	@Override
	@Transactional(readOnly = true)
//	@Cacheable(value = "UserService::getById", key = "#id")
	public Master getById(final Long id) {
		return masterRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException("User not found."));
	}
}
