package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.toolsOfMaster.BlackList;
import ru.alliedar.pokaznoi.repository.BlackListRepository;
import ru.alliedar.pokaznoi.service.BlackListService;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListRequestDto;
import ru.alliedar.pokaznoi.web.dto.toolsOfMaster.BlackListResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blackLists")
@RequiredArgsConstructor
public class BlackListController {

    private final BlackListRepository blackListRepository;
    private final BlackListService blackListService;

    @PostMapping("/create")
//    @PreAuthorize("@customSecurityExpression.canAccessMaster(#blackListRequestDto.master_id)")
    public ResponseEntity<BlackListResponseDto> create(final @RequestBody BlackListRequestDto blackListRequestDto) {
        BlackListResponseDto blackListResponseDto = blackListService.create(blackListRequestDto);
        return new ResponseEntity<>(blackListResponseDto, HttpStatus.CREATED);

    }


    @GetMapping
    public List<BlackList> getMasters() {
        List<BlackList> blackLists = blackListRepository.findAll();
        return blackLists;
    }

}
