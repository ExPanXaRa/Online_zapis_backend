package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderRepository orderRepository;

	@GetMapping("/getOrders")
	public List<Order> getOrders() {
		List<Order> order = orderRepository.findAll();
		return order;
	}
}
