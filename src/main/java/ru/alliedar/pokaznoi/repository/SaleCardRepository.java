package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.SaleCard;

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

}
