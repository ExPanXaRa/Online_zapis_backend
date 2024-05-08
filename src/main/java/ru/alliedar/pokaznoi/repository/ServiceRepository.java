package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alliedar.pokaznoi.domain.service.Service;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
	@Query(value = """
            SELECT exists(
                          SELECT 1
                          FROM services
                          WHERE master_id = :masterId
                          AND id = :serviceId
                          )
            """, nativeQuery = true)
	boolean isServiceOwner(@Param("masterId") Long masterId,
						   @Param("serviceId") Long serviceId);
}
