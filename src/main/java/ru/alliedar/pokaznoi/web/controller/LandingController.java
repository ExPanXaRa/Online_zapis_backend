package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.Landing;
import ru.alliedar.pokaznoi.repository.LandingRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/landings")
@RequiredArgsConstructor
@CrossOrigin
public class LandingController {

    private final LandingRepository landingRepository;
    @GetMapping
    @PreAuthorize("@customSecurityExpression.isAdmin()")
    public List<Landing> getLandings() {
        List<Landing> landings = landingRepository.findAll();
        return landings;
    }
}
