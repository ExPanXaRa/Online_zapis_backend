package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.order.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value = """
			              SELECT *
			              FROM orders
			              WHERE master_id = :masterId
			              AND DATE(time_of_start) = :dateOfStart
			""", nativeQuery = true)
	List<Order> findByMasterAndTimeOfCreateDate(@Param("masterId") Long masterId,
												@Param("dateOfStart") LocalDate dateOfStart);

}
