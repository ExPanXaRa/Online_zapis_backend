package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
