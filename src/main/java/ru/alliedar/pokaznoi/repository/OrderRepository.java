package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alliedar.pokaznoi.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
