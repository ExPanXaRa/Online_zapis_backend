package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@CrossOrigin
public class BlackListController {

    private final BlackListService blackListService;

    @PostMapping("/create")
    @PreAuthorize("@customSecurityExpression.canAccessMaster(#blackListRequestDto.master_id)")
    public ResponseEntity<BlackListResponseDto> create(final @RequestBody BlackListRequestDto blackListRequestDto) {
        BlackListResponseDto blackListResponseDto = blackListService.create(blackListRequestDto);
        return new ResponseEntity<>(blackListResponseDto, HttpStatus.CREATED);

    }

    @PostMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessBlackList(#id)")
    public ResponseEntity<BlackListResponseDto> update(@PathVariable("id") Long id, @RequestBody BlackListRequestDto blackListRequestDto) {
        BlackListResponseDto updatedBlackList = blackListService.update(id, blackListRequestDto);
        return ResponseEntity.ok(updatedBlackList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessBlackList(#id)")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        blackListService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    @PreAuthorize("@customSecurityExpression.isAdmin()")
    public ResponseEntity<List<BlackListResponseDto>> getAll() {
        List<BlackListResponseDto> blackLists = blackListService.getAll();
        return ResponseEntity.ok(blackLists);
    }

//    @GetMapping
//    public List<BlackList> getMasters() {
//        List<BlackList> blackLists = blackListRepository.findAll();
//        return blackLists;
//    }

}
