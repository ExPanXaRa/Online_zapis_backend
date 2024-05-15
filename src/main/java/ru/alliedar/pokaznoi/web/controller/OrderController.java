package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.repository.OrderRepository;
import ru.alliedar.pokaznoi.service.OrderService;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderUpdateClientDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderUpdateMasterDto;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;

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

	@PostMapping("/update/client/{id}")
	public ResponseEntity<OrderResponseDto> updateClient(@PathVariable Long id,
													  final @RequestBody OrderUpdateClientDto orderUpdateClientDto) {
		OrderResponseDto orderResponseDto = orderService.updateClient(orderUpdateClientDto, id);
		return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
	}

	@PostMapping("/update/master/{id}")
	public ResponseEntity<OrderResponseDto> updateMaster(@PathVariable Long id,
												   final @RequestBody OrderUpdateMasterDto orderUpdateMasterDto) {
		OrderResponseDto orderResponseDto = orderService.updateMaster(orderUpdateMasterDto, id);
		return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
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
