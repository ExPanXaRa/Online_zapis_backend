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

	@Query(value = """
            SELECT exists(
                          SELECT 1
                          FROM black_lists
                          WHERE master_id = :masterId
                          AND id = :blackListId
                          )
            """, nativeQuery = true)
	boolean isBlackListOwner(@Param("masterId") Long masterId,
						@Param("blackListId") Long blackListIdId);

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
