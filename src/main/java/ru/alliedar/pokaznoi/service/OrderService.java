package ru.alliedar.pokaznoi.service;

import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;

import java.time.LocalDateTime;

public interface OrderService {
	OrderResponseDto create(OrderRequestDto orderRequestDto);

	@Transactional
	void delete(Long orderId);
}
