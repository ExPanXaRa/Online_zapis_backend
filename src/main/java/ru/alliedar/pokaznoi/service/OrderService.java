package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;

public interface OrderService {
	OrderResponseDto create(OrderRequestDto orderRequestDto);
}
