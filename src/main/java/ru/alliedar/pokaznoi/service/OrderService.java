package ru.alliedar.pokaznoi.service;

import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderUpdateClientDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderUpdateMasterDto;

import java.time.LocalDateTime;

public interface OrderService {
	OrderResponseDto create(OrderRequestDto orderRequestDto);


	OrderResponseDto updateClient(OrderUpdateClientDto orderUpdateClientDto, Long orderId);

	OrderResponseDto updateMaster(OrderUpdateMasterDto orderUpdateMasterDto, Long orderId);

	@Transactional
	void delete(Long orderId);
}
