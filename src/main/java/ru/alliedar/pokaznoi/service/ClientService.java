package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientRequestDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientResponseDto;

import java.util.Optional;

public interface ClientService {

//	@Query(value = """
//			SELECT * FROM  sale_cards sc
//			JOIN clients_sale_cards csc ON csc.sale_card_id = sc.id
//			WHERE csc.client_id = :clientId
//			""", nativeQuery = true)
//List<SaleCard> findAllByClientId(
//				@Param("clientId") Long clientId
//		);
	Client getById(Long id);
	ClientResponseDto create(ClientRegisterDto clientRegisterDto);
}
