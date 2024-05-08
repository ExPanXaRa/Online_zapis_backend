package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
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
}
