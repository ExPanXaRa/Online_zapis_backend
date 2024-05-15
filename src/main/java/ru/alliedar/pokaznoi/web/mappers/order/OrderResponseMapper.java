package ru.alliedar.pokaznoi.web.mappers.order;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.order.Order;
import ru.alliedar.pokaznoi.web.dto.order.OrderResponseDto;
import ru.alliedar.pokaznoi.web.mappers.Mappable;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper extends Mappable<Order, OrderResponseDto> {
}
