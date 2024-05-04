package ru.alliedar.pokaznoi.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alliedar.pokaznoi.domain.master.Master;
import ru.alliedar.pokaznoi.repository.MasterRepository;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.web.dto.master.MasterLoginDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterRegisterDto;
import ru.alliedar.pokaznoi.web.dto.master.MasterResponseDto;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/masters")
@RequiredArgsConstructor
public class MasterController {

    private final MasterRepository masterRepository;
    private final MasterService masterService;
    private final StringRedisTemplate stringRedisTemplate;


    @PostMapping("/register")
    public ResponseEntity<MasterResponseDto> registerUser(
            @Validated(OnCreate.class) final @RequestBody MasterRegisterDto masterRegisterDto) {
        MasterResponseDto newUser = masterService.create(masterRegisterDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MasterResponseDto> loginUser(
            final @RequestBody MasterLoginDto masterLoginDto,
            final HttpServletResponse response,
            final HttpServletRequest request) {
        try {
            MasterResponseDto master = masterService.login(masterLoginDto);
            String key = UUID.randomUUID().toString();

            stringRedisTemplate.opsForValue()
                    .set(key, "M"+String.valueOf(master.getId()));

            Cookie cookie = new Cookie("sessionId", key);
            cookie.setPath("/");
            cookie.setMaxAge(15 * 24 * 60 * 60);
            response.addCookie(cookie);
            return ResponseEntity.ok(master);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(
            final @CookieValue(name = "sessionId") String sessionId,
            final HttpServletResponse response) {
        Boolean exists = stringRedisTemplate.hasKey(sessionId);

        if (exists != null && exists) {
            stringRedisTemplate.delete(sessionId);
            Cookie cookie = new Cookie("sessionId", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<Master> getMasters() {
        List<Master> masters = masterRepository.findAll();
        return masters;
    }
}
