package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.user.Role;
import ru.alliedar.pokaznoi.repository.MasterRepository;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRegisterDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRequestDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseDto;
import ru.alliedar.pokaznoi.web.mappers.master.MasterRegisterMapper;
import ru.alliedar.pokaznoi.web.mappers.master.MasterRequestMapper;
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

}
