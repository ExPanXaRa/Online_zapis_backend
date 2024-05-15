package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;

import java.math.BigDecimal;

public interface SaleCardRepository extends JpaRepository<SaleCard, Long> {
	@Query(value = """
            SELECT exists(
                          SELECT 1
                          FROM sale_cards
                          WHERE master_id = :masterId
                          AND id = :saleCardId
                          )
            """, nativeQuery = true)
	boolean isSaleCardOwner(@Param("masterId") Long masterId,
							@Param("saleCardId") Long saleCardId);

	@Query(value = """
   			SELECT percent
			FROM clients_sale_cards csc
			INNER JOIN sale_cards sc ON csc.sale_card_id = sc.id
			WHERE csc.client_id = :clientId AND sc.master_id = :masterId
			LIMIT 1
			""", nativeQuery = true)
	BigDecimal clientHasSale(@Param("clientId") Long clientId,
							 @Param("masterId") Long masterId);

}
