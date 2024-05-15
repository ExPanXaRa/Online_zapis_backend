package ru.alliedar.pokaznoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Landing;

public interface LandingRepository extends JpaRepository<Landing, Long> {
}
