package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.service.OrderService;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	@Override
	@Transactional
	public OrderResponseDto create(OrderRequestDto orderRequestDto) {
		return null;
	}
}
