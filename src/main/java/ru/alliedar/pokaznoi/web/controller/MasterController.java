package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.repository.MasterRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/masters")
@RequiredArgsConstructor
public class MasterController {

    private final MasterRepository masterRepository;

    @GetMapping
    public List<Master> getMasters() {
        List<Master> masters = masterRepository.findAll();
        return masters;
    }
}
