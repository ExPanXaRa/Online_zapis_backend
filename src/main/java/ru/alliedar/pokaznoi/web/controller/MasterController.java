package ru.alliedar.pokaznoi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.domain.service.Service;
import ru.alliedar.pokaznoi.repository.MasterRepository;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.web.dto.auth.UserRequestDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResponseDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRegisterDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRequestDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseDto;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/masters")
@RequiredArgsConstructor
public class MasterController {

    private final MasterRepository masterRepository;
    private final MasterService masterService;


    @PostMapping("/register")
    public ResponseEntity<MasterResponseDto> registerUser(
            @Validated(OnCreate.class) final @RequestBody MasterRegisterDto masterRegisterDto) {
        MasterResponseDto newUser = masterService.create(masterRegisterDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Master> getMasters() {
        List<Master> masters = masterRepository.findAll();
        return masters;
    }
}
