package ru.alliedar.pokaznoi.web.mappers.order;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.web.dto.order.OrderRequestDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper extends Mappable<Order, OrderRequestDto> {
}
