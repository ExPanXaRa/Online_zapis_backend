package ru.alliedar.pokaznoi.service;

import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.client.Client;
import ru.alliedar.pokaznoi.web.dto.client.ClientChangeDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientLoginDto;
import ru.alliedar.pokaznoi.web.dto.client.ClientRegisterDto;
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
	ClientResponseDto login(ClientLoginDto clientLoginDto);

//	@Transactional
//	Client findByMobileNumber(String phone);
	void clientSaveTokenTg(String phone, String rememberToken);

	//	@Override
	//	@Transactional
	//	public Client findByMobileNumber(String phone) {
	//		return clientRepository.findByMobileNumber(phone)
	//				.orElseThrow(() ->
	//						new ResourceNotFoundException("Client not found"));
	//	}
	@Transactional
	Client findByTelegramToken(String chatId);

	ClientResponseDto create(ClientRegisterDto clientRegisterDto);
	ClientResponseDto update(ClientChangeDto clientChangeDto);
}
