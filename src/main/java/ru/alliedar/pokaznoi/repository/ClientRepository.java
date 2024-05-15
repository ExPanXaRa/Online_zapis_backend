package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alliedar.pokaznoi.domain.client.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByMobileNumber(String number);

}
