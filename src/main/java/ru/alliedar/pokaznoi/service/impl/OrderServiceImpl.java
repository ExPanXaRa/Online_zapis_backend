package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.repository.OrderRepository;
import ru.alliedar.pokaznoi.repository.ServiceRepository;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.service.OrderService;
import ru.alliedar.pokaznoi.service.ServiceService;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;
import ru.alliedar.pokaznoi.web.dto.service.ServiceResponseDto;
import ru.alliedar.pokaznoi.web.mappers.order.OrderResponseMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final ServiceRepository serviceRepository;
	private final ServiceService serviceService;
	private final ClientService clientService;
	private final MasterService masterService;
	private final OrderResponseMapper orderResponseMapper;


	@Override
	@Transactional
	public OrderResponseDto create(OrderRequestDto orderRequestDto) {
		Client optionalClient = clientService
				.getById(orderRequestDto.getClient_id());

		Master optionalMaster = masterService
				.getById(orderRequestDto.getMaster_id());

		if (optionalMaster != null && optionalClient != null) {
			Order newOrder = new Order();

			BigDecimal newPrice = BigDecimal.valueOf(0);
			LocalDateTime orderTimeEnd = orderRequestDto.getTimeOfStart().toLocalDateTime();
			List<ru.alliedar.pokaznoi.domain.service.Service> services = orderRequestDto.getServices();
			for (ru.alliedar.pokaznoi.domain.service.Service service : services) {
				ServiceResponseDto currentService = serviceService.getById(service.getId());
				newPrice = newPrice.add(currentService.getPrice());
//				orderTimeEnd = orderTimeEnd.((currentService.getStandardTime());
				String timeString = String.valueOf(currentService.getStandardTime());
				String[] timeParts = timeString.split(":");

				int hours = Integer.parseInt(timeParts[0]);
				int minutes = Integer.parseInt(timeParts[1]);
				int seconds = Integer.parseInt(timeParts[2]);

				orderTimeEnd = orderTimeEnd.plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);

			}
			newOrder.setPrice(newPrice);

			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("GMT+6"));
			LocalDateTime orderTimeStart = orderRequestDto.getTimeOfStart().toLocalDateTime();
//			LocalDateTime orderTimeEnd = orderRequestDto.getTimeOfEnd().toLocalDateTime();

			List<Order> masterAndTimeOfCreateDate = orderRepository.findByMasterAndTimeOfCreateDate(optionalMaster.getId(),
					orderRequestDto.getTimeOfStart().toLocalDateTime().toLocalDate());
			if (orderTimeStart.toLocalDate().isAfter(currentTime.toLocalDate()) ||
					orderTimeStart.toLocalDate().isEqual(currentTime.toLocalDate())
							&& orderTimeStart.toLocalTime().isAfter(currentTime
							.toLocalTime())) {
				if (masterAndTimeOfCreateDate.size() != 0) {
					for (Order orderMaster : masterAndTimeOfCreateDate) {
						LocalDateTime currentOrderMasterStart = orderMaster.getTimeOfStart().toLocalDateTime();
						LocalDateTime currentOrderMasterEnd = orderMaster.getTimeOfEnd().toLocalDateTime();
						// проеверка если время начала заказа валидна по сравнению с текушим
						if (orderTimeStart.isBefore(currentOrderMasterStart) && orderTimeStart.isAfter(currentOrderMasterEnd) &&
								orderTimeEnd.isBefore(currentOrderMasterStart) && orderTimeEnd.isAfter(currentOrderMasterEnd) &&
								!(orderTimeStart.isAfter(currentOrderMasterStart) && orderTimeEnd.isBefore(currentOrderMasterEnd)) &&
								!(currentOrderMasterStart.isAfter(orderTimeStart) && currentOrderMasterEnd.isBefore(orderTimeEnd))) {

							newOrder.setTimeOfStart(orderRequestDto.getTimeOfStart());
							newOrder.setTimeOfEnd(Timestamp.valueOf(orderTimeEnd));
							newOrder.setClient(optionalClient);
							newOrder.setMaster(optionalMaster);
						} else {
							throw new IllegalArgumentException("Начало или конец заказа не валидны");
						}

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
			throw new IllegalArgumentException("Клиетна или мастера не существует");
		}
	}
}
