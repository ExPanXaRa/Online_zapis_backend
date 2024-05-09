package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.repository.OrderRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.service.OrderService;
import ru.alliedar.pokaznoi.service.SaleCardService;
import ru.alliedar.pokaznoi.service.ServiceService;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;
import ru.alliedar.pokaznoi.web.mappers.order.OrderResponseMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final ServiceService serviceService;
	private final ClientService clientService;
	private final MasterService masterService;
	private final SaleCardService saleCardService;
	private final OrderResponseMapper orderResponseMapper;


	private boolean isValidOrderTime(LocalDateTime orderTimeStart, LocalDateTime orderTimeEnd, LocalDateTime currentTime) {
		if (orderTimeStart.toLocalDate().isAfter(currentTime.toLocalDate()) ||
				(orderTimeStart.toLocalDate().isEqual(currentTime.toLocalDate()) &&
						orderTimeStart.toLocalTime().isAfter(currentTime.toLocalTime()))) {
			return true;
		}
		return false;
	}

	private boolean isOrderTimeValidForMaster(List<Order> masterAndTimeOfCreateDate, LocalDateTime orderTimeStart, LocalDateTime orderTimeEnd) {
		for (Order orderMaster : masterAndTimeOfCreateDate) {
			LocalDateTime currentOrderMasterStart = orderMaster.getTimeOfStart().toLocalDateTime();
			LocalDateTime currentOrderMasterEnd = orderMaster.getTimeOfEnd().toLocalDateTime();

			if (orderTimeStart.isBefore(currentOrderMasterStart) && orderTimeStart.isAfter(currentOrderMasterEnd) &&
					orderTimeEnd.isBefore(currentOrderMasterStart) && orderTimeEnd.isAfter(currentOrderMasterEnd) &&
					!(orderTimeStart.isAfter(currentOrderMasterStart) && orderTimeEnd.isBefore(currentOrderMasterEnd)) &&
					!(currentOrderMasterStart.isAfter(orderTimeStart) && currentOrderMasterEnd.isBefore(orderTimeEnd))) {
				return true;
			}
		}
		return false;
	}

	private BigDecimal calculateDiscountPrice(Long clientId, Long masterId, BigDecimal initialPrice) {
		BigDecimal discount = saleCardService.clientHasSale(clientId, masterId);
		if (discount != null) {
			BigDecimal discountedPrice = initialPrice.subtract(initialPrice.multiply(discount.divide(BigDecimal.valueOf(100))));
			return discountedPrice;
		} else {
			return initialPrice;
		}
	}


	@Override
	@Transactional
	public OrderResponseDto create(OrderRequestDto orderRequestDto) {
		Client optionalClient = clientService.getById(orderRequestDto.getClient_id());
		Master optionalMaster = masterService.getById(orderRequestDto.getMaster_id());

		if (optionalMaster != null && optionalClient != null) {
			Order newOrder = new Order();
			BigDecimal newPrice = BigDecimal.valueOf(0);
			LocalDateTime orderTimeEnd = orderRequestDto.getTimeOfStart().toLocalDateTime();
			List<ru.alliedar.pokaznoi.domain.service.Service> services = orderRequestDto.getServices();
			for (ru.alliedar.pokaznoi.domain.service.Service service : services) {
				ServiceResponseDto currentService = serviceService.getById(service.getId());
				newPrice = newPrice.add(currentService.getPrice());
				String timeString = String.valueOf(currentService.getStandardTime());
				String[] timeParts = timeString.split(":");
				int hours = Integer.parseInt(timeParts[0]);
				int minutes = Integer.parseInt(timeParts[1]);
				int seconds = Integer.parseInt(timeParts[2]);
				orderTimeEnd = orderTimeEnd.plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
			}
			newPrice = calculateDiscountPrice(orderRequestDto.getClient_id(), orderRequestDto.getMaster_id(), newPrice);
			newPrice = newPrice.setScale(2, RoundingMode.HALF_UP);
			newOrder.setPrice(newPrice);

			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("GMT+6"));
			LocalDateTime orderTimeStart = orderRequestDto.getTimeOfStart().toLocalDateTime();
			List<Order> masterAndTimeOfCreateDate = orderRepository.findByMasterAndTimeOfCreateDate(optionalMaster.getId(),
					orderRequestDto.getTimeOfStart().toLocalDateTime().toLocalDate());

			if (isValidOrderTime(orderTimeStart, orderTimeEnd, currentTime)) {
				if (masterAndTimeOfCreateDate.size() != 0) {
					if (isOrderTimeValidForMaster(masterAndTimeOfCreateDate, orderTimeStart, orderTimeEnd)) {
						newOrder.setTimeOfStart(orderRequestDto.getTimeOfStart());
						newOrder.setTimeOfEnd(Timestamp.valueOf(orderTimeEnd));
						newOrder.setClient(optionalClient);
						newOrder.setMaster(optionalMaster);
					} else {
						throw new IllegalArgumentException("Начало или конец заказа не валидны");
					}
				} else {
					newOrder.setTimeOfStart(orderRequestDto.getTimeOfStart());
					newOrder.setTimeOfEnd(Timestamp.valueOf(orderTimeEnd));
					newOrder.setClient(optionalClient);
					newOrder.setMaster(optionalMaster);
				}

				Timestamp timestamp = Timestamp.valueOf(currentTime);
				newOrder.setTimeOfCreate(timestamp);
				orderRepository.save(newOrder);
				return orderResponseMapper.toDto(newOrder);
			} else {
				throw new IllegalArgumentException("Указанное начальное время или дата заказа раньше текущего");
			}

		} else {
			throw new IllegalArgumentException("Клиент или мастер не существует");
		}
	}

	@Override
	@Transactional
	public void delete(Long orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if (optionalOrder.isPresent()) {
			orderRepository.delete(optionalOrder.get());
		} else {
			throw new IllegalArgumentException("Заказ с указанным идентификатором не найден: " + orderId);
		}
	}
}
