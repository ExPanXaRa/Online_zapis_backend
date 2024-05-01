package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alliedar.pokaznoi.domain.service.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
