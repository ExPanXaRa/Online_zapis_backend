package ru.alliedar.pokaznoi.telegram.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.repository.UserRepository;
import ru.alliedar.pokaznoi.telegram.service.ZapisKMasteruService;

@Service
@RequiredArgsConstructor
public class ZapisKMasteruServiceImpl implements ZapisKMasteruService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public String getUsers() {
        User user =  userRepository.findById(1L).orElseThrow(() ->
                new ResourceNotFoundException("User not found."));
        return user.toString();
    }
}
