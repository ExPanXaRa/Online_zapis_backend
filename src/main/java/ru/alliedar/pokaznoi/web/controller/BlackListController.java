package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;
import ru.alliedar.pokaznoi.repository.BlackListRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blackLists")
@RequiredArgsConstructor
public class BlackListController {

    private final BlackListRepository blackListRepository;
    @GetMapping
    public List<BlackList> getMasters() {
        List<BlackList> blackLists = blackListRepository.findAll();
        return blackLists;
    }

}
