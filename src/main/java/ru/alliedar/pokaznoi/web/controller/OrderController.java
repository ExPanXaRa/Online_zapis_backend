package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;
import ru.alliedar.pokaznoi.repository.OrderRepository;
import ru.alliedar.pokaznoi.service.OrderService;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
	private final OrderRepository orderRepository;
	private final OrderService orderService;

	@PostMapping("/create")
	public ResponseEntity<OrderResponseDto> create(final @RequestBody OrderRequestDto orderRequestDto) {
		OrderResponseDto orderResponseDto = orderService.create(orderRequestDto);
		return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		orderService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	@PreAuthorize("@customSecurityExpression.isAdmin()")
	public List<Order> getOrders() {
		List<Order> order = orderRepository.findAll();
		return order;
	}
}
