package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.domain.master.Master;

import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master, Long> {
	Optional<Master> findByMobileNumber(String number);
	Optional<Master> findByEmail(String email);

}
